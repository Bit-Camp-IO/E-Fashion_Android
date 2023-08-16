package com.bitio.ui.bottom_nav_rotue

sealed class HomeRouteScreens(val route:String){
    object Home : HomeRouteScreens(route = "home_route")
    object Cart : HomeRouteScreens(route = "cart_route")
    object Favorite : HomeRouteScreens(route = "favorite_route")
    object Profile : HomeRouteScreens(route = "profile_route")
}