package com.bitio.ui.product.home

sealed class HomeRouteScreens(val route:String) {
    object Filters: HomeRouteScreens(route = "filters_route")
    object ProductDetails: HomeRouteScreens(route = "product_details_route")
    object Offers: HomeRouteScreens(route = "offers_route")
    object Zara: HomeRouteScreens(route = "zara_route")

    // Other Screens
}