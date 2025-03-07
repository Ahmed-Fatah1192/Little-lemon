package com.example.littlelemon.repository

import com.example.littlelemon.database.MenuDao
import com.example.littlelemon.database.MenuItemEntity
import com.example.littlelemon.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MenuRepository(private val menuDao: MenuDao) {

    suspend fun fetchAndStoreMenu() {
        withContext(Dispatchers.IO) {
            val menuItems = ApiService.fetchMenu().map { MenuItemEntity.fromNetwork(it) }
            menuDao.insertMenuItems(menuItems) // Using the correct method name
        }
    }

    fun getMenu(): Flow<List<MenuItemEntity>> = menuDao.getAllMenuItems()
}