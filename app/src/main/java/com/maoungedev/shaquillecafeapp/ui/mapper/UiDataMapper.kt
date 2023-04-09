package com.maoungedev.shaquillecafeapp.ui.mapper

import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.ui.model.CaffeMenuData

object UiDataMapper {

    fun mapDomainToPresentationData(caffeMenu: CaffeMenu): CaffeMenuData =
        CaffeMenuData(
            idMenu =caffeMenu.idMenu,
            menuCaffeName = caffeMenu.menuCaffeName,
            category = caffeMenu.category,
            description = caffeMenu.description,
            price = caffeMenu.price,
            photo = caffeMenu.photo,
        )

}