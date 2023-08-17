package com.bitio.efashion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.route.authGraph
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens
import com.bitio.ui.bottom_nav_rotue.cartRoute
import com.bitio.ui.bottom_nav_rotue.favoriteRoute
import com.bitio.ui.bottom_nav_rotue.homeGraph
import com.bitio.ui.bottom_nav_rotue.profileGraph
import com.bitio.ui.order_status.OrderStatusViewModel
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.profile.ProfileViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    authenticationViewModel: AuthenticationViewModel,
    orderStatusViewModel: OrderStatusViewModel
) {

    val isLogin = true
    val startDestination = if (isLogin) HomeRouteScreens.Home.route else "auth"

    NavHost(navController = navController, startDestination = startDestination) {

        homeGraph(navController)

        cartRoute(navController)
        favoriteRoute(navController, favoriteViewModel)

        profileGraph(navController, profileViewModel,orderStatusViewModel)

        authGraph(
            navController = navController,
            authenticationViewModel = authenticationViewModel
        )
    }
}