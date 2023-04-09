package com.maoungedev.shaquillecafeapp.domain.model

import com.squareup.moshi.Json

data class OrderMenu(
    val orderId: String = "",
    val tableNumber: String = "",
    val category: String = "",
    val menuCaffeName: String = "",
    val description: String = "",
    val price: String = "",
    val photo: String = "",
    val time: String = "",
    var isDeleted: Boolean = false
)
