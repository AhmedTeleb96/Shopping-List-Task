package com.example.shopping_list_module.domain.repositories

import com.example.shopping_list_module.data.models.ShoppingItemDto
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository
{
    fun getShoppingItems(): Flow<List<ShoppingItemDto>>
    suspend fun addItem(item: ShoppingItemDto)
    suspend fun updateItem(item: ShoppingItemDto)
    suspend fun deleteItem(id: String)
    suspend fun syncWithRemote()
}