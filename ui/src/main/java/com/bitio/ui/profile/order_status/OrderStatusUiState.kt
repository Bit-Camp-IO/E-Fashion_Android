package com.bitio.ui.profile.order_status

data class OrderStatusUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val ordersStatus: List<OrderStatus> = emptyList(),
)

data class OrderStatus(
    val title: String = "",
    val description: String = "",
    val isOrderStatusActive: Boolean = false,
    val imageOfOrderStatus: Int = 0
)