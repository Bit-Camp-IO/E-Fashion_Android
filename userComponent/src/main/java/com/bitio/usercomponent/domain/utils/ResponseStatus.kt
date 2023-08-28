package com.bitio.usercomponent.domain.utils

sealed class ResponseStatus<out T>()  {
    data class Success<T>(val data: T) : ResponseStatus<T>()
    data class Error(val errorMessage: String) : ResponseStatus<Nothing>()
}