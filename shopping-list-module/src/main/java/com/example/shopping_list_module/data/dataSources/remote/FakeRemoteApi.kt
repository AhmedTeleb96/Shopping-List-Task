package com.example.shopping_list_module.data.dataSources.remote

import com.example.shopping_list_module.data.models.ShoppingItemDto
import kotlinx.coroutines.delay
import java.util.UUID

class FakeRemoteApi {
    private val remoteItems = mutableListOf(
        ShoppingItemDto(
            id = "1",
            name = "Apples",
            quantity = "4",
            note = "Green ones",
            isBought = false
        ),
        ShoppingItemDto(
            id = "2",
            name = "Milk",
            quantity = "2",
            note = "Low fat",
            isBought = true
        ),
        ShoppingItemDto(
            id = "3",
            name = "Bread",
            quantity = "1",
            isBought = false
        ),
        ShoppingItemDto(
            id = "4",
            name = "Oranges",
            quantity = "12",
            isBought = false
        ),
        ShoppingItemDto(
            id = "5",
            name = "Coffee",
            quantity = "14",
            note = "Mt7weg",
            isBought = true
        ),
        ShoppingItemDto(
            id = "6",
            name = "Chicken",
            quantity = "3",
            note = "Frozen",
            isBought = false
        )
    )

    suspend fun getItems(): List<ShoppingItemDto> {
        delay(500) // Simulate network delay
        return remoteItems
    }

    suspend fun syncItems(items: List<ShoppingItemDto>) {
        delay(500)
        remoteItems.clear()
        remoteItems.addAll(items.shuffled())
    }
}