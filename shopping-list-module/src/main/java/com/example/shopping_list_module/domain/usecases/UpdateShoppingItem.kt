package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.entities.ShoppingItem
import com.example.shopping_list_module.domain.repositories.ShoppingListRepository

class UpdateShoppingItem(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(item: ShoppingItem) = repository.updateItem(item)
}
