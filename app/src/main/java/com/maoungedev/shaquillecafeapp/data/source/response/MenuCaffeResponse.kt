package com.maoungedev.shaquillecafeapp.data.source.response

import com.squareup.moshi.Json

data class MenuCaffeResponse(

    @field:Json(name = "id_menu")
    val idMenu: String? = null,

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

)
