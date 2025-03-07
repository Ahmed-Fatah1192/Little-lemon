package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.littlelemon.database.AppDatabase
import com.example.littlelemon.repository.MenuRepository
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.viewmodel.MenuViewModel
import com.example.littlelemon.viewmodel.MenuViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create database instance
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little_lemon_db"
        ).build()

        // Create repository and view model
        val menuDao = database.menuDao()
        val menuRepository = MenuRepository(menuDao)
        val factory = MenuViewModelFactory(menuRepository)
        menuViewModel = ViewModelProvider(this, factory)[MenuViewModel::class.java]

        setContent {
            LittleLemonTheme {
                Surface {  // Ensures Material Theme is correctly applied
                    AppNavigation(menuViewModel)
                }
            }
        }
    }
}