package com.example.shopping_list_module.presentation.shoppingList

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.shopping_list_module.di.IoDispatcher
import com.example.shopping_list_module.domain.entities.ShoppingItem
import com.example.shopping_list_module.domain.usecases.AddShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.DeleteShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.GetShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.SyncShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.UpdateShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val syncShoppingItemsUseCase: SyncShoppingItemsUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _shoppingItems = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingItems: StateFlow<List<ShoppingItem>> = _shoppingItems.asStateFlow()

    private val _filterBought = MutableStateFlow(false)
    val filterBought: StateFlow<Boolean> = _filterBought.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortAsc = MutableStateFlow(true)
    val sortAsc: StateFlow<Boolean> = _sortAsc.asStateFlow()

    init {
        getShoppingItems()
        // Automatically sync items when initialized
        syncWithRemote()


        // Schedule periodic sync
        schedulePeriodicSync()
    }

    private fun getShoppingItems() {
        viewModelScope.launch(ioDispatcher) {
            getShoppingItemsUseCase().collect { items ->
                Log.i("zzz", "items: $items")
                _shoppingItems.value = items
            }
        }
    }

    fun addItem(name: String, quantity: String, note: String?) {
        val item = ShoppingItem(
            id = ((_shoppingItems.value.maxByOrNull { it.id.toInt() }?.id?.toInt() ?: 7) +1).toString(),
            name = name,
            quantity = quantity,
            note = note
        )
        viewModelScope.launch(ioDispatcher) {
           addShoppingItemUseCase(item)
            syncWithRemote()
        }
    }

    private fun updateItem(item: ShoppingItem) {
        viewModelScope.launch(ioDispatcher) {
            updateShoppingItemUseCase(item)
            syncWithRemote()
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch(ioDispatcher) {
            deleteShoppingItemUseCase(id)
            syncWithRemote()
        }
    }

    fun toggleItemBought(item: ShoppingItem) {
        updateItem(item.copy(isBought = !item.isBought, updatedAt = System.currentTimeMillis()))
    }

    fun setFilterBought(showBought: Boolean) {
        _filterBought.value = showBought
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSortOrder() {
        _sortAsc.value = !_sortAsc.value
    }

    fun getFilteredSortedItems(): StateFlow<List<ShoppingItem>> =
        combine(
            _shoppingItems,
            _filterBought,
            _searchQuery,
            _sortAsc
        ) { items, filterBought, searchQuery, sortAsc ->
            items.filter {
                (filterBought || !it.isBought) &&
                        (it.name.contains(searchQuery, true) || it.note?.contains(searchQuery, true) == true)
            }.sortedWith(
                if (sortAsc) compareBy { it.updatedAt }
                else compareByDescending { it.updatedAt }
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun syncWithRemote() {
        viewModelScope.launch(ioDispatcher) {
            syncShoppingItemsUseCase()
            getShoppingItems()
        }
    }

    private fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<ShoppingSyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "ShoppingSyncWork",
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
    }
}
