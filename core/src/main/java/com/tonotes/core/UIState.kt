package com.tonotes.core

sealed class UIState<out T> {
    object Idle : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T?) : UIState<T>()
    data class Fail(val message: String?) : UIState<Nothing>()
    data class Error(val message: String?) : UIState<Nothing>()
}