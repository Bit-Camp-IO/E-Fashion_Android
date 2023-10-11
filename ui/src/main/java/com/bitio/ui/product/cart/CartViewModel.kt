package com.bitio.ui.product.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.cart.AddlCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.DeleteCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.GetAllCartsUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CartViewModel(
    private val getAllCartsUseCase: GetAllCartsUseCase,
    private val addCartUseCase: AddlCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase
) : ViewModel() {

    private val _cartUiState = mutableStateOf(CartItemUiState())
    val cartUiState = _cartUiState

    init {
        viewModelScope.launch {
            getAllCartsUseCase().onSuccess { cart ->
                cart?.let {
                    _cartUiState.value = CartItemUiState(
                        items = it.items,
                        subTotal = it.subTotal,
                        tax = it.tax,
                        total = it.total,
                        totalQuantity = it.totalQuantity,
                    )
                }
            }
            getAllCartsUseCase().onFailure {
                _cartUiState.value = CartItemUiState(
                    message = it.message.toString()
                )
            }
        }
    }

    fun deleteCart(cardId: String) {
        viewModelScope.launch {
            val result = deleteCartUseCase(cardId)
            if (result.isSuccess) {
                result.map {
                    _cartUiState.value = CartItemUiState(message = it.toString())
                }
            } else {
                result.map {
                    _cartUiState.value = CartItemUiState(message = it.toString())
                }
            }
        }
    }
}