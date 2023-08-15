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

    fun onProgressOrder(status: Boolean) {
        _checkOrderUiState.update {
            it.copy(
                isProgress = status,
            )
        }
    }

    fun onWayOrder(status: Boolean) {
        _checkOrderUiState.update {
            it.copy(isItOnWay = status)
        }
    }

    fun onClickDeliver(status: Boolean) {
        _checkOrderUiState.update {
            it.copy(isDelivered = status)
        }
    }

}