package com.maoungedev.shaquillecafeapp.utils

import com.maoungedev.shaquillecafeapp.BuildConfig

object Helper {

    fun generateImage(path: String): String {
        return "${BuildConfig.BASE_URL}$path"
    }

    fun generateTextPrice(price: String): String {
        return "Rp.$price"
    }

}