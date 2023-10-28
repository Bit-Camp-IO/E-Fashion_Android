package com.bitio.ui.profile.order_status

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.order.GetAllOrdersUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class OrderStatusViewModel(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel() {


    private val _orderUiState = mutableStateOf(OrderUiState())
    val orderUiState = _orderUiState

    init {
        getAllOrders()
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            _orderUiState.value = OrderUiState(
                isLoading = true
            )
            val result = getAllOrdersUseCase()
            result.onSuccess {
                it?.let { orders ->
                    _orderUiState.value = OrderUiState(
                        isLoading = false,
                        orders = orders
                    )
                }
            }
            result.onFailure {
                _orderUiState.value = OrderUiState(
                    isLoading = false,
                    errorMessage = it.message.toString()
                )
            }
        }
    }
}