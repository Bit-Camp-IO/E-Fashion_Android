package com.bitio.efashion

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitio.ui.authentication.AuthenticationViewModel
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens
import com.bitio.ui.order_status.OrderStatusViewModel
import com.bitio.ui.product.favorite.FavoriteViewModel
import com.bitio.ui.profile.ProfileViewModel
import kotlin.math.log

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    authenticationViewModel: AuthenticationViewModel,
    orderStatusViewModel: OrderStatusViewModel
) {
    val navController = rememberNavController()
    var isNavBottomVisible by remember {
        mutableStateOf(false)
    }

    isNavBottomVisible = authenticationViewModel.checkIfLogin.value


    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, visibility = isNavBottomVisible)
        },
    ) {
        AppNavGraph(
            navController = navController,
            favoriteViewModel = favoriteViewModel,
            profileViewModel = profileViewModel,
            authenticationViewModel = authenticationViewModel,
            orderStatusViewModel = orderStatusViewModel
        )
    }
}


@Composable
fun BottomBar(navController: NavHostController, visibility: Boolean) {

    val screens = listOf(
        HomeRouteScreens.Home,
        HomeRouteScreens.Cart,
        HomeRouteScreens.Favorite,
        HomeRouteScreens.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (visibility) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                screens.forEach {
                    BottomItem(
                        screen = it,
                        navController = navController,
                        currentNavDestination = currentDestination
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.BottomItem(
    screen: HomeRouteScreens,
    navController: NavHostController,
    currentNavDestination: NavDestination?
) {

    val selected = currentNavDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.secondary,
            unselectedIconColor = Color.White,
            indicatorColor = MaterialTheme.colorScheme.onBackground,
        ),
        alwaysShowLabel = false,
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.route,
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                navController.graph.startDestinationRoute?.let {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
