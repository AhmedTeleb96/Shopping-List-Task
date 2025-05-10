package com.example.shopping_list_module.data.dataSources.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shopping_list_module.data.models.ShoppingItemDto

@Dao
interface ShoppingListDao
{
    @Query("SELECT * FROM shopping_items")
    fun getItems(): List<ShoppingItemDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItemDto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItems(items: List<ShoppingItemDto>)

    @Update
    suspend fun updateItem(item: ShoppingItemDto)

    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun deleteItem(id: String)
}