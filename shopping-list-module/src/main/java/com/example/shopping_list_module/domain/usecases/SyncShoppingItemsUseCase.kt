package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class SyncShoppingItemsUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke() = repository.syncWithRemote()
}