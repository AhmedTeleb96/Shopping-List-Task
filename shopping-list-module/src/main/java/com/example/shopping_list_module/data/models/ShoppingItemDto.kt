package com.example.shopping_list_module.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopping_list_module.domain.entities.ShoppingItem

@Entity(tableName = "shopping_items")
data class ShoppingItemDto(
    @PrimaryKey val id: String,
    val name: String,
    val quantity: String,
    val note: String? = null,
    val isBought: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)


fun ShoppingItemDto.toDomainEntity() = ShoppingItem(id,name,quantity,note ?: "",isBought,updatedAt)
fun List<ShoppingItemDto>.toDomainEntities() = map {ShoppingItem(it.id,it.name,it.quantity,it.note ?: "",it.isBought,it.updatedAt)}

fun ShoppingItem.toModel() = ShoppingItemDto(id,name,quantity, note,isBought,updatedAt)
fun List<ShoppingItem>.toModels() = map {ShoppingItemDto(it.id,it.name,it.quantity, it.note,it.isBought,it.updatedAt)}
