package com.bitio.efashion

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitio.ui.bottom_nav_rotue.HomeRouteScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        AppNavGraph(navController = navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.parent?.route
}

@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        HomeRouteScreens.Home,
        HomeRouteScreens.Cart,
        HomeRouteScreens.Favorite,
        HomeRouteScreens.Profile,
    )
    val currentDestination = currentRoute(navController)
    Surface {
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

@Composable
fun RowScope.BottomItem(
    screen: HomeRouteScreens,
    navController: NavHostController,
    currentNavDestination: String?
) {

    val selected = currentNavDestination == screen.route

    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.background,
        ),
        alwaysShowLabel = true,
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.route
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
