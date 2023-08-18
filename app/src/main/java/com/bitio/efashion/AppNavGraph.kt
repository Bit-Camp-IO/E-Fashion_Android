package com.bitio.efashion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.authentication.route.authGraph
import com.bitio.ui.route.RootRouteScreens
import com.bitio.ui.route.cartRoute
import com.bitio.ui.route.favoriteRoute
import com.bitio.ui.route.homeGraph
import com.bitio.ui.route.profileGraph
import com.bitio.ui.profile.order_status.OrderStatusViewModel
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.profile.ProfileViewModel

@Composable
fun AppNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    authenticationViewModel: AuthenticationViewModel,
    orderStatusViewModel: OrderStatusViewModel,
) {

    val startDestination =
        if (authenticationViewModel.checkIfLogin.value) RootRouteScreens.Home.route else "auth"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {

        homeGraph(navController)

        cartRoute(navController)
        favoriteRoute(navController, favoriteViewModel)

        profileGraph(navController, profileViewModel, orderStatusViewModel)

        authGraph(
            navController = navController,
            authenticationViewModel = authenticationViewModel
        )
    }
}