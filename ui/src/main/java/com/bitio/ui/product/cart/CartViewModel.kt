package com.bitio.ui.product.cart

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.cart.AddlCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.DeleteCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.EditCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.GetAllCartsUseCase
import com.bitio.utils.TAG_APP
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CartViewModel(
    private val getAllCartsUseCase: GetAllCartsUseCase,
    private val addCartUseCase: AddlCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val editCartUseCase: EditCartUseCase
) : ViewModel() {

    private val _cartUiState = mutableStateOf(CartItemUiState())
    val cartUiState = _cartUiState

    init {
        viewModelScope.launch {
            getAllCartsUseCase().onSuccess { cart ->
                cart?.let {
                    _cartUiState.value = CartItemUiState(
                        items = it.items,
                        subtotal = it.subtotal,
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
                Log.d(TAG_APP, "init: ${it.message}")
            }
        }
    }

    fun deleteCart(cartId: String) {
        viewModelScope.launch {
            val result =  deleteCartUseCase(cartId)
            result.onSuccess { cart ->
                cart?.let {
                    _cartUiState.value = CartItemUiState(
                        items = it.items,
                        subtotal = it.subtotal,
                        tax = it.tax,
                        total = it.total,
                        totalQuantity = it.totalQuantity,
                    )
                }
            }
            result.onFailure {
                _cartUiState.value = CartItemUiState(
                    message = it.message.toString()
                )
            }
        }
    }

    fun editCart(cartId: String,quantity:Int) {
        viewModelScope.launch {
            val result =  editCartUseCase(cartId,quantity)
            result.onSuccess { cart ->
                cart?.let {
                    _cartUiState.value = CartItemUiState(
                        items = it.items,
                        subtotal = it.subtotal,
                        tax = it.tax,
                        total = it.total,
                        totalQuantity = it.totalQuantity,
                    )
                }
            }
            result.onFailure {
                _cartUiState.value = CartItemUiState(
                    message = it.message.toString()
                )
            }
        }
    }
}