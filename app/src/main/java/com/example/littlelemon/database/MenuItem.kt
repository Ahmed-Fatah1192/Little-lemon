package com.example.littlelemon.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemon.network.MenuItemNetwork
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
) {
    companion object {
        fun fromNetwork(menuItem: MenuItemNetwork): MenuItemEntity {
            return MenuItemEntity(
                id = menuItem.id,
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image,
                category = menuItem.category
            )
        }
    }
}