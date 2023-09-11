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

sealed class UiDataState2<T:Any> {
    class Loading<T:Any>: UiDataState2<T>()
    data class Success<T : Any>(val data: T) : UiDataState2<T>()
    class Error <T:Any>(message: String?) : UiDataState2<T>() {
        val message: String
        init {
            this.message = message ?: "Unknown Error"
        }

    }
}
