package com.bitio.efashion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.route.AuthRouterScreens
import com.bitio.ui.authentication.route.authGraph
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens
import com.bitio.ui.bottom_nav_rotue.cartRoute
import com.bitio.ui.bottom_nav_rotue.favoriteRoute
import com.bitio.ui.bottom_nav_rotue.homeRoute
import com.bitio.ui.bottom_nav_rotue.profileRoute
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.profile.ProfileViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    authenticationViewModel: AuthenticationViewModel
) {

    val isLogin = true
    val startDestination = if (isLogin) HomeRouteScreens.Home.route else "auth"

    NavHost(navController = navController, startDestination = startDestination) {

        homeRoute(navController)
        cartRoute(navController)
        favoriteRoute(navController, favoriteViewModel)
        profileRoute(navController, profileViewModel)

        authGraph(
            navController = navController,
            authenticationViewModel = authenticationViewModel
        )
    }
}