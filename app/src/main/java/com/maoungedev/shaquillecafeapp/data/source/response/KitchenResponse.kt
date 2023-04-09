package com.maoungedev.shaquillecafeapp.data.source.response

import com.squareup.moshi.Json

data class KitchenResponse(
    @field:Json(name = "id_pesanan")
    val orderId: String? = null,

    @field:Json(name = "nomor_meja")
    val tableNumber: String? = null,

    @field:Json(name = "kategori")
    val category: String? = null,

    @field:Json(name = "menu")
    val menuCaffeName: String? = null,

    @field:Json(name = "deskripsi")
    val description: String? = null,

    @field:Json(name = "harga")
    val price: String? = null,

    @field:Json(name = "foto")
    val photo: String? = null,

    @field:Json(name = "waktu")
    val time: String? = null

)
