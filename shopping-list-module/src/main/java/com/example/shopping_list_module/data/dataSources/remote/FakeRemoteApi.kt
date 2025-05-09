package com.example.shopping_list_module.data.dataSources.remote

import com.example.shopping_list_module.data.models.ShoppingItemDto
import kotlinx.coroutines.delay

class FakeRemoteApi
{
    private val remoteItems = mutableListOf<ShoppingItemDto>()

    suspend fun getItems(): List<ShoppingItemDto> {
        delay(500) // Simulate network delay
        return remoteItems
    }

    suspend fun syncItems(items: List<ShoppingItemDto>) {
        delay(500)
        remoteItems.clear()
        remoteItems.addAll(items)
    }
}