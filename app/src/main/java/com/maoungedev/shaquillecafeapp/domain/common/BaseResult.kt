package com.maoungedev.shaquillecafeapp.domain.common

sealed class BaseResult<out T, out U> {
    data class Success<out T>(val data: T): BaseResult<T, Nothing>()
    data class Error<out U>(val message: U): BaseResult<Nothing, U>()
}
