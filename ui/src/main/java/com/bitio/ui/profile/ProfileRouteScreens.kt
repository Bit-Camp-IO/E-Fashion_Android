package com.bitio.ui.profile

sealed class ProfileRouteScreens(val route: String) {
    object Location : ProfileRouteScreens(route = "location_route")
    object OrdersStatus : ProfileRouteScreens(route = "orders_status_route")
    object ChatSupport : ProfileRouteScreens(route = "chat_support_route")
    object Notifications : ProfileRouteScreens(route = "notifications_route")
}