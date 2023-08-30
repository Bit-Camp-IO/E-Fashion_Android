package com.bitio.ui.shared.screenState

sealed interface UiDataState {
    object Loading : UiDataState
    object Success : UiDataState
    class Error(message: String?) : UiDataState {
        val message: String

        init {
            this.message = message ?: "Unknown Error"
        }

    }

}