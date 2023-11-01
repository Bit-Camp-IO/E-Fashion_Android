package com.bitio.ui.product.favorite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.GetProductDetailsUseCase
import com.bitio.productscomponent.domain.useCase.favorite.AddProductToCartUseCase
import com.bitio.productscomponent.domain.useCase.favorite.DeleteFavoriteOfUserUseCase
import com.bitio.productscomponent.domain.useCase.favorite.GetAllFavoritesOfUserUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class FavoriteViewModel(
    private val getAllFavoritesOfUserUseCase: GetAllFavoritesOfUserUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val deleteFavoriteOfUserUseCase: DeleteFavoriteOfUserUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel() {

    private val _favoriteUIState = mutableStateOf(FavoriteUiState())
    val favoriteUIState = _favoriteUIState

    private val _productUIState = mutableStateOf(ProductUiState())
    val productUIState = _productUIState

    private val _cartUIState = mutableStateOf(CartUiState())
    val cartUIState = _cartUIState


    init {
        getAllFavoritesOfUser()
    }

    private fun getAllFavoritesOfUser() {
        viewModelScope.launch {
            _favoriteUIState.value = FavoriteUiState(isLoading = true)
            val result = getAllFavoritesOfUserUseCase()
            result.onSuccess {
                it.data?.let { favorites ->
                    _favoriteUIState.value = FavoriteUiState(
                        isLoading = false,
                        products = favorites
                    )
                }
            }
            result.onFailure {
                _favoriteUIState.value = FavoriteUiState(
                    isLoading = false,
                    errorMessage = it.message.toString()
                )
            }
        }
    }

    fun getProductDetails(productId: String) {
        viewModelScope.launch {
            _productUIState.value = ProductUiState(isLoading = true)
            val result = getProductDetailsUseCase(productId)
            result.onSuccess {
                it.let { product ->
                    _productUIState.value = ProductUiState(
                        isLoading = false,
                        product = product
                    )
                }
            }
            result.onFailure {
                _productUIState.value = ProductUiState(
                    isLoading = false,
                    errorMessage = it.message.toString()
                )
            }
        }
    }

    fun deleteFavoriteOfUser(productId: String) {
        viewModelScope.launch {
            deleteFavoriteOfUserUseCase(productId)
            getAllFavoritesOfUser()
        }
    }

    fun addProductToCart(cartItemUiState: CartItemUiState) {
        viewModelScope.launch {
            _cartUIState.value = CartUiState(isLoading = true)
            val result = addProductToCartUseCase(cartItemUiState)
            result?.onSuccess {
                it?.let {
                    _cartUIState.value = CartUiState(
                        isLoading = false,
                        message = it
                    )
                }
            }
            result.onFailure {
                _cartUIState.value = CartUiState(
                    isLoading = false,
                    message = it.message.toString()
                )
            }
        }
    }
}