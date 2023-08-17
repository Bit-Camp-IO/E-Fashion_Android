package com.bitio.ui.profile.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.location.LocationScreen
import com.bitio.ui.order_status.OrderStatusScreen
import com.bitio.ui.order_status.OrderStatusViewModel
import com.bitio.ui.profile.chat.ChatScreen
import com.bitio.ui.profile.notifications.NotificationsScreen

internal fun NavController.navigateToLocationScreen() {
    navigate(ProfileRouteScreens.Location.route)
}

internal fun NavGraphBuilder.locationRoute(navController: NavController) {
    composable(ProfileRouteScreens.Location.route) {
        LocationScreen(
            navController = navController
        )
    }
}

/*--------------------  Order Status Route  --------------------*/
internal fun NavController.navigateToOrderStatusScreen() {
    navigate(ProfileRouteScreens.OrderStatus.route)
}

internal fun NavGraphBuilder.orderStatusRoute(
    navController: NavController,
    orderStatusViewModel: OrderStatusViewModel
) {
    composable(ProfileRouteScreens.OrderStatus.route) {
        OrderStatusScreen(
            orderStatusViewModel = orderStatusViewModel,
            navController = navController
        )
    }
}

/*--------------------  Chat Support Route  --------------------*/
internal fun NavController.navigateToChatSupportScreen() {
    navigate(ProfileRouteScreens.ChatSupport.route)
}

internal fun NavGraphBuilder.chatSupportRoute(navController: NavController) {
    composable(ProfileRouteScreens.ChatSupport.route) {
        ChatScreen(
            navController = navController
        )
    }
}


/*--------------------  Notifications Route  --------------------*/
internal fun NavController.navigateToNotificationsScreen() {
    navigate(ProfileRouteScreens.Notifications.route)
}

internal fun NavGraphBuilder.notificationsRoute(navController: NavController) {
    composable(ProfileRouteScreens.Notifications.route) {
        NotificationsScreen(
            navController = navController
        )
    }
}
