package com.bitio.ui.profile.order_status

import com.bitio.productscomponent.domain.model.order.Order


data class OrderUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val orders: List<Order> = emptyList(),
)
