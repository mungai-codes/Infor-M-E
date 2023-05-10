package com.mungai.common

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}