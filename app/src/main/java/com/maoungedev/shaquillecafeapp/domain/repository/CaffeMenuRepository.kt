package com.maoungedev.shaquillecafeapp.domain.repository

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import kotlinx.coroutines.flow.Flow

interface CaffeMenuRepository {

    suspend fun getMenuCaffeByCategory(category: String): Flow<BaseResult<List<CaffeMenu>, String>>
    suspend fun getAllMenu(): Flow<BaseResult<List<CaffeMenu>, String>>

}