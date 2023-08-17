package com.bitio.ui.profile.route

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitio.ui.profile.location.LocationScreen
import com.bitio.ui.order_status.OrderStatusScreen
import com.bitio.ui.order_status.OrderStatusViewModel
import com.bitio.ui.profile.chat.ChatScreen
import com.bitio.ui.profile.notifications.NotificationsScreen
import com.bitio.ui.profile.route.Routes.CHAT_SUPPORT_ROUTE
import com.bitio.ui.profile.route.Routes.LOCATION_ROUTE
import com.bitio.ui.profile.route.Routes.NOTIFICATIONS_ROUTE
import com.bitio.ui.profile.route.Routes.ORDER_STATUS_ROUTE

internal fun NavController.navigateToLocationScreen() {
    navigate(LOCATION_ROUTE)
}

internal fun NavGraphBuilder.locationRoute(navController: NavController) {
    composable(LOCATION_ROUTE) {
        LocationScreen(
            navController = navController
        )
    }
}

/*--------------------  Order Status Route  --------------------*/
internal fun NavController.navigateToOrderStatusScreen() {
    navigate(ORDER_STATUS_ROUTE)
}

internal fun NavGraphBuilder.orderStatusRoute(
    navController: NavController,
    orderStatusViewModel: OrderStatusViewModel
) {
    composable(ORDER_STATUS_ROUTE) {
        OrderStatusScreen(
            orderStatusViewModel = orderStatusViewModel,
            navController = navController
        )
    }
}

/*--------------------  Chat Support Route  --------------------*/
internal fun NavController.navigateToChatSupportScreen() {
    navigate(CHAT_SUPPORT_ROUTE)
}

internal fun NavGraphBuilder.chatSupportRoute(navController: NavController) {
    composable(CHAT_SUPPORT_ROUTE) {
        ChatScreen(
            navController = navController
        )
    }
}


/*--------------------  Notifications Route  --------------------*/
internal fun NavController.navigateToNotificationsScreen() {
    navigate(NOTIFICATIONS_ROUTE)
}

internal fun NavGraphBuilder.notificationsRoute(navController: NavController) {
    composable(NOTIFICATIONS_ROUTE) {
        NotificationsScreen(
            navController = navController
        )
    }
}


private object Routes {
    const val ORDER_STATUS_ROUTE = "order_status_route"
    const val LOCATION_ROUTE = "location_route"
    const val CHAT_SUPPORT_ROUTE = "chat_support_route"
    const val NOTIFICATIONS_ROUTE = "notifications_route"

}