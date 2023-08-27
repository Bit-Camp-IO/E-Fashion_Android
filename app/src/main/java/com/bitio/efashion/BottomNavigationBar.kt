package com.bitio.efashion

import android.annotation.SuppressLint
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
import com.bitio.ui.profile.ProfileRouteScreens
import com.bitio.ui.route.RootRouteScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(checkIfLogin: Boolean) {

    val navController = rememberNavController()

    var isNavBottomVisible by remember {
        mutableStateOf(false)
    }
    isNavBottomVisible = checkIfLogin

    Scaffold(
        bottomBar = {
            val visibility = currentRoute(navController = navController) !in listOf(
                ProfileRouteScreens.Location.route,
                ProfileRouteScreens.ChatSupport.route,
            )
            BottomBar(navController, visibility)
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        AppNavGraph(innerPadding, navController, isNavBottomVisible)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


@Composable
fun BottomBar(navController: NavHostController, visibility: Boolean) {

    val screens = listOf(
        RootRouteScreens.Home,
        RootRouteScreens.Cart,
        RootRouteScreens.Favorite,
        RootRouteScreens.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (visibility) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onBackground,
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
    screen: RootRouteScreens,
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
