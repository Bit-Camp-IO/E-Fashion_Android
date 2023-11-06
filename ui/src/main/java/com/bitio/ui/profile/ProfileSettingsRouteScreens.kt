package com.bitio.ui.profile

sealed class ProfileSettingsRouteScreens(val route: String) {
    object Location : ProfileSettingsRouteScreens(route = "location_route")
    object OrdersStatus : ProfileSettingsRouteScreens(route = "orders_status_route")
    object ChatSupport : ProfileSettingsRouteScreens(route = "chat_support_route")
    object Notifications : ProfileSettingsRouteScreens(route = "notifications_route")
    object UserProfile : ProfileSettingsRouteScreens(route = "user_profile_route")
    object ChangePassword : ProfileSettingsRouteScreens(route = "change_password_route")
    object ChangeEmail : ProfileSettingsRouteScreens(route = "change_email_route")
}