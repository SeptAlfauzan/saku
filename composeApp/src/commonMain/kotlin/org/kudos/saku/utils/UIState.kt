package org.kudos.saku.utils

sealed class UIState<out T>{
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val error: String) : UIState<T>()
    data object Loading : UIState<Nothing>()
}