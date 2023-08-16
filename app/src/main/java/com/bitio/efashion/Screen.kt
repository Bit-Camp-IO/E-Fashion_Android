package com.bitio.efashion

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_route")
    object Cart : Screen(route = "cart_route")
    object Favorite : Screen(route = "favorite_route")
    object Profile : Screen(route = "profile_route")
}