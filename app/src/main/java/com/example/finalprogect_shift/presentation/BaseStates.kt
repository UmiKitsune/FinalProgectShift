package com.example.finalprogect_shift.presentation

import okhttp3.ResponseBody

sealed class BaseStates<out T> {

    object Loading: BaseStates<Nothing>()

    object EmptyField: BaseStates<Nothing>()

    object FilledField: BaseStates<Nothing>()

    object OutOfAmountRange: BaseStates<Nothing>()

    data class SuccessResponseBody<out T>(val value: T): BaseStates<T>()

    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ): BaseStates<Nothing>()

}