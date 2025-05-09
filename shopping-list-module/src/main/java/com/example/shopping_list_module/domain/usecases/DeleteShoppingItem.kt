package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository

class DeleteShoppingItem(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(id: String) = repository.deleteItem(id)
}