package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.screens.MenuScreen
import com.example.littlelemon.viewmodel.MenuViewModel

@Composable
fun AppNavigation(menuViewModel: MenuViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Onboarding.route) { OnboardingScreen() }
        composable(Home.route) { HomeScreen(navController, menuViewModel) }
        composable(Profile.route) { ProfileScreen(navController) }
    }
}