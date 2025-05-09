package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.entities.ShoppingItem
import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingItems(private val repository: ShoppingListRepository) {
    operator fun invoke(): Flow<List<ShoppingItem>> = repository.getShoppingItems()
}