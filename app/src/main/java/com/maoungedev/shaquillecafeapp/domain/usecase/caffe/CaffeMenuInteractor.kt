package com.maoungedev.shaquillecafeapp.domain.usecase.caffe

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.repository.CaffeMenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CaffeMenuInteractor @Inject constructor(
    private val caffeMenuRepository: CaffeMenuRepository
): CaffeMenuUseCase {
    override suspend fun getMenuCaffeByCategory(category: String): Flow<BaseResult<List<CaffeMenu>, String>> {
        return caffeMenuRepository.getMenuCaffeByCategory(category)
    }
}