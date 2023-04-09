package com.maoungedev.shaquillecafeapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaffeMenuData(
    val idMenu: String = "",
    val category: String = "",
    val menuCaffeName: String = "",
    val description: String = "",
    val price: String = "",
    val photo: String = "",

) : Parcelable
