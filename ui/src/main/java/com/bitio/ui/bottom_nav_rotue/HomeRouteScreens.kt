package com.bitio.ui.bottom_nav_rotue

import androidx.annotation.DrawableRes
import com.bitio.ui.R

sealed class HomeRouteScreens(val route: String, @DrawableRes val icon: Int) {
    object Home : HomeRouteScreens(route = "home_route",icon = R.drawable.home)
    object Cart : HomeRouteScreens(route = "cart_route",icon = R.drawable.bag)
    object Favorite : HomeRouteScreens(route = "favorite_route",icon = R.drawable.outline_heart)
    object Profile : HomeRouteScreens(route = "profile_route",icon = R.drawable.profile)
}