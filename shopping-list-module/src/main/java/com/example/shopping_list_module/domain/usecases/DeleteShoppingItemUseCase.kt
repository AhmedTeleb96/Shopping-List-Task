package com.example.shopping_list_module.domain.usecases

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class DeleteShoppingItemUseCase @Inject constructor(private val repository: ShoppingListRepository) {
    suspend operator fun invoke(id: String) = repository.deleteItem(id)
}