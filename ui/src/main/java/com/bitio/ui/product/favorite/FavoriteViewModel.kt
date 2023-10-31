package com.bitio.ui.product.favorite

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.GetProductDetailsUseCase
import com.bitio.productscomponent.domain.useCase.favorite.GetAllFavoritesOfUserUseCase
import com.bitio.utils.TAG_APP
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class FavoriteViewModel(
    private val getAllFavoritesOfUserUseCase: GetAllFavoritesOfUserUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _favoriteUIState = mutableStateOf(FavoriteUiState())
    val favoriteUIState = _favoriteUIState

    private val _productUIState = mutableStateOf(ProductUiState())
    val productUIState = _productUIState

    init {
        getAllFavoritesOfUser()
    }

    private fun getAllFavoritesOfUser() {
        viewModelScope.launch {
            _favoriteUIState.value = FavoriteUiState(isLoading = true)
            getAllFavoritesOfUserUseCase().collect { result ->
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
}