package com.bitio.ui.product.favorite

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.favorite.GetAllFavoritesOfUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class FavoriteViewModel(
    private val getAllFavoritesOfUserUseCase: GetAllFavoritesOfUserUseCase
) : ViewModel() {

    private val _favoriteUIState = mutableStateOf(FavoriteUiState())
    val favoriteUIState = _favoriteUIState

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
}