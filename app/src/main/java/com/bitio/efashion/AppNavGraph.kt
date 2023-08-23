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
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    checkIfLogin: Boolean
) {

    val startDestination = if (checkIfLogin) RootRouteScreens.Home.route else "auth"
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        homeGraph(navController)
        cartRoute(navController)
        favoriteRoute(navController)
        profileGraph(navController)
        authGraph(navController)
    }
}