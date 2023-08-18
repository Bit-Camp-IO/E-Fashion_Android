package com.bitio.ui.route

import androidx.annotation.DrawableRes
import com.bitio.ui.R

sealed class RootRouteScreens(val route: String, @DrawableRes val icon: Int) {
    object Home : RootRouteScreens(route = "home_route",icon = R.drawable.home)
    object Cart : RootRouteScreens(route = "cart_route",icon = R.drawable.bag)
    object Favorite : RootRouteScreens(route = "favorite_route",icon = R.drawable.outline_heart)
    object Profile : RootRouteScreens(route = "profile_route",icon = R.drawable.profile)
}