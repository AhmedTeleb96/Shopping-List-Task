package com.example.shopping_list_module.di

import android.content.Context
import com.example.shopping_list_module.data.dataSources.localDB.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): ShoppingDatabase =
        ShoppingDatabase.getInstance(appContext)

    @Provides
    fun provideShoppingListDao(database: ShoppingDatabase) = database.shoppingListDao()

}