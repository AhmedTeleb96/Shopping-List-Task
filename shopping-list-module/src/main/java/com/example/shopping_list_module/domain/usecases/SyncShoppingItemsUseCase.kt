package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository

class SyncShoppingItemsUseCase(private val repository: ShoppingListRepository) {
    suspend operator fun invoke() = repository.syncWithRemote()
}