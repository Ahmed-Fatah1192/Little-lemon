package com.example.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.database.MenuItemEntity
import com.example.littlelemon.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {

    val menuItems: Flow<List<MenuItemEntity>> = repository.getMenu() // Observes data from Room

    fun refreshMenu() {
        viewModelScope.launch {
            repository.fetchAndStoreMenu()
        }
    }
}