package com.example.shopping_list_module.di

import com.example.shopping_list_module.domain.repositories.ShoppingListRepository
import com.example.shopping_list_module.domain.usecases.AddShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.DeleteShoppingItemUseCase
import com.example.shopping_list_module.domain.usecases.GetShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.SyncShoppingItemsUseCase
import com.example.shopping_list_module.domain.usecases.UpdateShoppingItemUseCase
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
    fun provideGetShoppingItemsUseCases(repository: ShoppingListRepository) =  GetShoppingItemsUseCase(repository)

    @Provides
    @Singleton
    fun provideAddShoppingItemUseCases(repository: ShoppingListRepository) =  AddShoppingItemUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateShoppingItemUseCases(repository: ShoppingListRepository) =  UpdateShoppingItemUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteShoppingItemUseCases(repository: ShoppingListRepository) =  DeleteShoppingItemUseCase(repository)

    @Provides
    @Singleton
    fun provideSyncShoppingItemsUseCases(repository: ShoppingListRepository) =  SyncShoppingItemsUseCase(repository)
}