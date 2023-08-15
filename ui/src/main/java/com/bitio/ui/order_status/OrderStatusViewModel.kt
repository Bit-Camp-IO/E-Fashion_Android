package com.bitio.ui.order_status

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor() : ViewModel() {

    private val _checkOrderUiState = MutableStateFlow(OrderStatusUiState())
    val checkOrderString = _checkOrderUiState.asStateFlow()

    init {
        _checkOrderUiState.update {
            it.copy(
                isLoading = true
            )
        }
        getOrderStatus()
    }

    private fun getOrderStatus() {
        _checkOrderUiState.update {
            it.copy(
                isLoading = false,
                ordersStatus = listOf(
                    OrderStatus(
                        title = "On Progress",
                        description = "Your order still on progress it may take hours",
                        isOrderStatusActive = true,
                        typeOrderStatus = TypeOrderStatus.OnProgress
                    ),
                    OrderStatus(
                        title = "On its way",
                        description = "The delivery man on his way to your location",
                        isOrderStatusActive = false,
                        typeOrderStatus = TypeOrderStatus.OnWay
                    ),
                    OrderStatus(
                        title = "Delivered",
                        description = "Your order has been delivered, hope you liked it",
                        isOrderStatusActive = false,
                        typeOrderStatus = TypeOrderStatus.Delivered
                    )
                )
            )
        }
    }
}