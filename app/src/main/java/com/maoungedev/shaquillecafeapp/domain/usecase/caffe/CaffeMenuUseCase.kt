package com.maoungedev.shaquillecafeapp.domain.usecase.caffe

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import kotlinx.coroutines.flow.Flow

interface CaffeMenuUseCase {

    suspend fun getMenuCaffeByCategory(category: String):  Flow<BaseResult<List<CaffeMenu>, String>>

}