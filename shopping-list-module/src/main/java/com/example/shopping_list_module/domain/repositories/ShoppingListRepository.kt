package com.example.shopping_list_module.domain.repositories

import com.example.shopping_list_module.data.models.ShoppingItemDto
import com.example.shopping_list_module.domain.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository
{
    fun getShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun addItem(item: ShoppingItem)
    suspend fun updateItem(item: ShoppingItem)
    suspend fun deleteItem(id: String)
    suspend fun syncWithRemote()
}