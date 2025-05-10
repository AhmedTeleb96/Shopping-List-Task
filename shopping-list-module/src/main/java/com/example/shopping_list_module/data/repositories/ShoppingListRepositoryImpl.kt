package com.example.shopping_list_module.data.repositories;

import com.example.shopping_list_module.data.dataSources.localDB.ShoppingListDao
import com.example.shopping_list_module.data.dataSources.remote.FakeRemoteApi
import com.example.shopping_list_module.data.models.ShoppingItemDto
import com.example.shopping_list_module.data.models.toDomainEntities
import com.example.shopping_list_module.data.models.toModel
import com.example.shopping_list_module.domain.entities.ShoppingItem
import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShoppingListRepositoryImpl(
        private val dao:ShoppingListDao,
        private val remoteApi:FakeRemoteApi
) : ShoppingListRepository {

    override fun getShoppingItems(): Flow<List<ShoppingItem>> = flow {
        emit(dao.getItems().toDomainEntities())
    }

    override suspend fun addItem(item: ShoppingItem) {
        dao.insertItem(item.toModel())
    }

    override suspend fun updateItem(item: ShoppingItem) {
        dao.updateItem(item.toModel())
    }

    override suspend fun deleteItem(id: String) {
        dao.deleteItem(id)
    }

    override suspend fun syncWithRemote() {
        val remoteItems = remoteApi.getItems()
        dao.insertItems(remoteItems)
    }
}
