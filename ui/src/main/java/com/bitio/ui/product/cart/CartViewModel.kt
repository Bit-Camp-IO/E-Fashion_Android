package com.bitio.ui.product.cart

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.cart.AddProductToCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.DeleteProductFromCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.EditProductOfCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.GetAllProductsFromCartUseCase
import com.bitio.utils.TAG_APP
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CartViewModel(
    private val getAllProductsFromCartUseCase: GetAllProductsFromCartUseCase,
    private val addCartUseCase: AddProductToCartUseCase,
    private val deleteProductFromCartUseCase: DeleteProductFromCartUseCase,
    private val editProductOfCartUseCase: EditProductOfCartUseCase
) : ViewModel() {

    private val _cartUiState = mutableStateOf(CartItemUiState())
    val cartUiState = _cartUiState

    init {
        viewModelScope.launch {
            getAllProductsFromCartUseCase().onSuccess { cart ->
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
            getAllProductsFromCartUseCase().onFailure {
                _cartUiState.value = CartItemUiState(
                    message = it.message.toString()
                )
                Log.d(TAG_APP, "init: ${it.message}")
            }
        }
    }

    fun deleteCart(cartId: String) {
        viewModelScope.launch {
            val result = deleteProductFromCartUseCase(cartId)
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

    fun editCart(cartId: String, quantity: Int) {
        viewModelScope.launch {
            editProductOfCartUseCase(cartId, quantity).collect { result ->
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
}