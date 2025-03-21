package com.example.littlelemon


interface Destinations {
    val route: String

    //val icon: ImageVector
    val title: String
}

object Onboarding : Destinations {
    override val route = "Onboarding"
    override val title = "Onboarding"
}

object Home : Destinations {
    override val route = "Home"

    //    override val icon   = Icons.Filled.Home
    override val title = "Home Screen"
}

object Profile : Destinations {
    override val route = "Profile"

    //    override val icon   = Icons.Outlined.Person
    override val title = "Home Screen"
}