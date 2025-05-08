package com.example.shopping_list_module.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItemDto(
    @PrimaryKey val id: String,
    val name: String,
    val quantity: String,
    val note: String? = null,
    val isBought: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
