package com.bitio.ui.authentication

data class AuthUiState(
    val loading: Boolean = false,
    val state: String = "",
    val errorMessage: String = ""
)