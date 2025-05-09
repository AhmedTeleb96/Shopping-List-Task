package com.example.shopping_list_module.data.repositories;

import com.example.shopping_list_module.data.dataSources.localDB.ShoppingListDao;
import com.example.shopping_list_module.data.dataSources.remote.FakeRemoteApi;
import com.example.shopping_list_module.data.models.ShoppingItemDto
import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

public class ShoppingListRepositoryImpl(
        private val dao:ShoppingListDao,
        private val remoteApi:FakeRemoteApi
) : ShoppingListRepository {

    override fun getShoppingItems(): Flow<List<ShoppingItemDto>> = dao.getItems()

    override suspend fun addItem(item: ShoppingItemDto) {
        dao.insertItem(item)
    }

    override suspend fun updateItem(item: ShoppingItemDto) {
        dao.updateItem(item)
    }

    override suspend fun deleteItem(id: String) {
        dao.deleteItem(id)
    }

    override suspend fun syncWithRemote() {
        val localItems = dao.getItems().first()
        remoteApi.syncItems(localItems)
    }
}
