package com.bitio.ui.order_status

data class OrderStatusUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val ordersStatus: List<OrderStatus> = emptyList(),
)

data class OrderStatus(
    val title: String = "",
    val description: String = "",
    val isOrderStatusActive: Boolean = false,
    val typeOrderStatus: TypeOrderStatus = TypeOrderStatus.OnProgress
)

enum class TypeOrderStatus {
    OnProgress, OnWay, Delivered
}
/*
*  0 => OnProgress
*  1 => OnWay
*  2 => Delivered
* */
