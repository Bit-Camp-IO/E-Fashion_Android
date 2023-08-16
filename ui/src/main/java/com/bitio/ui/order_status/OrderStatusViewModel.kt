package com.bitio.ui.order_status

import androidx.lifecycle.ViewModel
import com.bitio.ui.R
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
                        isOrderStatusActive = false,
                       imageOfOrderStatus = R.drawable.order_progress
                    ),
                    OrderStatus(
                        title = "On its way",
                        description = "The delivery man on his way to your location",
                        isOrderStatusActive = false,
                        imageOfOrderStatus = R.drawable.order_on_way
                    ),
                    OrderStatus(
                        title = "Delivered",
                        description = "Your order has been delivered, hope you liked it",
                        isOrderStatusActive = false,
                        imageOfOrderStatus = R.drawable.order_delivered
                    )
                )
            )
        }
    }
}