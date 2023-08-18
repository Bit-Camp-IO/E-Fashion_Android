package com.bitio.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class FavoriteViewModel  : ViewModel() {

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