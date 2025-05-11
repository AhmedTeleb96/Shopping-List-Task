package com.example.shopping_list_module.di

import com.example.shopping_list_module.data.dataSources.localDB.ShoppingListDao
import com.example.shopping_list_module.data.dataSources.remote.FakeRemoteApi
import com.example.shopping_list_module.data.repositories.ShoppingListRepositoryImpl
import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule
{
    @Provides
    @Singleton
    fun provideShoppingListRepository(
        dao: ShoppingListDao,
        api: FakeRemoteApi
    ): ShoppingListRepository = ShoppingListRepositoryImpl(dao, api)
}