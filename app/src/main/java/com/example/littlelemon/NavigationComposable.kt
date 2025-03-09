package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(navController: NavHostController, sharedPreferences: SharedPreferences, menuItems: List<MenuItemRoom>) {
    val isLogin = sharedPreferences.getString("Email", "")?.isNotEmpty() == true

    NavHost(navController = navController, startDestination = if (isLogin) Home.route else Onboarding.route) {
        composable(Onboarding.route) {
            OnboardingScreen(navController, sharedPreferences)
        }
        composable(Home.route) {
            HomeScreen(navController, menuItems)
        }
        composable(Profile.route) {
            ProfileScreen(navController, sharedPreferences)
        }
    }
}