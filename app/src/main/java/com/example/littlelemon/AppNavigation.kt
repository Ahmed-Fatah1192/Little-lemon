package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.viewmodel.MenuViewModel

@Composable
fun AppWithNavigation(
    menuViewModel: MenuViewModel,
    startDestination: String = Onboarding.route
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) { OnboardingScreen(navController) }
        composable(Home.route) { HomeScreen(navController, menuViewModel) }
        composable(Profile.route) { ProfileScreen(navController) }
    }
}