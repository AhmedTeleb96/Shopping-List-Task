package com.example.shopping_list_module.domain.entities

data class ShoppingItem(
    val id: String,
    val name: String,
    val quantity: String,
    val note: String? = null,
    val isBought: Boolean = false ,
    val updatedAt: Long = System.currentTimeMillis(),
)
