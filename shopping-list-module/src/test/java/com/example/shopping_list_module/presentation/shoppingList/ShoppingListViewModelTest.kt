package com.example.shopping_list_module.presentation.shoppingList

import android.content.Context
import com.example.shopping_list_module.domain.usecases.AddShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.DeleteShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.GetShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.SyncShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.UpdateShoppingItemUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingListViewModelTest {

    private val addShoppingItemUseCase: AddShoppingItemUseCase = mockk(relaxed = true)
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase = mockk(relaxed = true)
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase = mockk()
    private val syncShoppingItemsUseCase: SyncShoppingItemsUseCase = mockk(relaxed = true)
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase = mockk(relaxed = true)
    private val context: Context = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ShoppingListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock getShoppingItemsUseCase to emit an empty list
        coEvery { getShoppingItemsUseCase() } returns flowOf(emptyList())

        viewModel = ShoppingListViewModel(
            addShoppingItemUseCase,
            deleteShoppingItemUseCase,
            getShoppingItemsUseCase,
            syncShoppingItemsUseCase,
            updateShoppingItemUseCase,
            context,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addItem should call AddShoppingItemUseCase and trigger sync`() = runTest {
        val itemName = "Apples"
        val quantity = "2kg"
        val note = "Green ones"

        viewModel.addItem(itemName, quantity, note)
        advanceUntilIdle()

        coVerify { addShoppingItemUseCase(any()) }
        coVerify { syncShoppingItemsUseCase() }
    }

    @Test
    fun `deleteItem should call DeleteShoppingItemUseCase and trigger sync`() = runTest {
        val itemId = "10"
        viewModel.deleteItem(itemId)
        advanceUntilIdle()

        coVerify { deleteShoppingItemUseCase(itemId) }
        coVerify { syncShoppingItemsUseCase() }
    }

    @Test
    fun `setFilterBought should update filterBought state`() = runTest {
        viewModel.setFilterBought(true)
        assertEquals(true, viewModel.filterBought.value)
    }

    @Test
    fun `toggleSortOrder should flip sortAsc`() = runTest {
        val original = viewModel.sortAsc.value
        viewModel.toggleSortOrder()
        assertEquals(!original, viewModel.sortAsc.value)
    }

    @Test
    fun `setSearchQuery should update searchQuery state`() = runTest {
        viewModel.setSearchQuery("milk")
        assertEquals("milk", viewModel.searchQuery.value)
    }
}
