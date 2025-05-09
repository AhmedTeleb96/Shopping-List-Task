package com.example.shopping_list_module.presentation.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_list_module.domain.entities.ShoppingItem
import com.example.shopping_list_module.domain.usecases.AddShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.DeleteShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.GetShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.SyncShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.UpdateShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val syncShoppingItemsUseCase: SyncShoppingItemsUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
) : ViewModel() {

    private val _shoppingItems = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingItems: StateFlow<List<ShoppingItem>> = _shoppingItems

    private val _filterBought = MutableStateFlow(false)
    private val _searchQuery = MutableStateFlow("")
    private val _sortAsc = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            getShoppingItemsUseCase().collect { items ->
                _shoppingItems.value = items
            }
        }
    }

    fun addItem(name: String, quantity: String, note: String?) {
        val item = ShoppingItem(
            id = UUID.randomUUID().toString(),
            name = name,
            quantity = quantity,
            note = note
        )
        viewModelScope.launch {
           addShoppingItemUseCase(item)
        }
    }

    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch {
            updateShoppingItemUseCase(item)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            deleteShoppingItemUseCase(id)
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

    fun getFilteredSortedItems(): StateFlow<List<ShoppingItem>> = MutableStateFlow(
        _shoppingItems.value.filter {
            (_filterBought.value || !it.isBought) &&
                    (it.name.contains(_searchQuery.value, true) || it.note?.contains(_searchQuery.value, true) == true)
        }.sortedWith(
            if (_sortAsc.value)
                compareBy { it.updatedAt }
            else
                compareByDescending { it.updatedAt }
        )
    )

    fun syncWithRemote() {
        viewModelScope.launch {
            syncShoppingItemsUseCase()
        }
    }
}
