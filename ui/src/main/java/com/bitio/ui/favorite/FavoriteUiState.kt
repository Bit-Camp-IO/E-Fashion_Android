package com.bitio.ui.favorite

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val state: List<Favorite> = emptyList(),
    val error: String = ""
)