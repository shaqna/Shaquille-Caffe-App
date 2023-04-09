package com.maoungedev.shaquillecafeapp.data.source.response

data class GeneralFetchResponse<T>(
    val state: String,
    val message: String,
    val data: T?
)
