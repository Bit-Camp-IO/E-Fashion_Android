package com.bitio.ui.product.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _favoriteUIState = MutableStateFlow(FavoriteUiState())
    val favoriteUIState: StateFlow<FavoriteUiState> = _favoriteUIState.asStateFlow()

    init {
        getFavoritesItems()
    }

    private fun getFavoritesItems() {
        viewModelScope.launch {
            _favoriteUIState.update { it.copy(isLoading = true) }
            _favoriteUIState.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }
}