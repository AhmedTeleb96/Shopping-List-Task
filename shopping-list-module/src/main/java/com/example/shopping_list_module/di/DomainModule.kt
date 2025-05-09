package com.example.shopping_list_module.di

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import com.example.shopping_list_module.domain.usecases.AddShoppingItem
import com.example.shopping_list_module.domain.usecases.DeleteShoppingItem
import com.example.shopping_list_module.domain.usecases.GetShoppingItems
import com.example.shopping_list_module.domain.usecases.SyncShoppingItems
import com.example.shopping_list_module.domain.usecases.UpdateShoppingItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule
{
    @Provides
    @Singleton
    fun provideGetShoppingItemsUseCases(repository: ShoppingListRepository) =  GetShoppingItems(repository)

    @Provides
    @Singleton
    fun provideAddShoppingItemUseCases(repository: ShoppingListRepository) =  AddShoppingItem(repository)

    @Provides
    @Singleton
    fun provideUpdateShoppingItemUseCases(repository: ShoppingListRepository) =  UpdateShoppingItem(repository)

    @Provides
    @Singleton
    fun provideDeleteShoppingItemUseCases(repository: ShoppingListRepository) =  DeleteShoppingItem(repository)

    @Provides
    @Singleton
    fun provideSyncShoppingItemsUseCases(repository: ShoppingListRepository) =  SyncShoppingItems(repository)
}