package com.bitio.ui.order_status

data class OrderStatusUiState(
    val isProgress: Boolean = false,
    val isItOnWay: Boolean = false,
    val isDelivered: Boolean = false,
)
