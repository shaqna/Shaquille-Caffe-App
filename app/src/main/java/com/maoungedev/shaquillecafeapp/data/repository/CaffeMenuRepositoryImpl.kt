package com.maoungedev.shaquillecafeapp.data.repository

import com.maoungedev.shaquillecafeapp.data.source.service.ApiService
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.repository.CaffeMenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CaffeMenuRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CaffeMenuRepository {
    override suspend fun getMenuCaffeByCategory(category: String): Flow<BaseResult<List<CaffeMenu>, String>> {
        return flow {
            val response = apiService.fetchMenuByCategory(category)
            val caffeMenu = arrayListOf<CaffeMenu>()
            if (!response.data.isNullOrEmpty()) {
                response.data.forEach {
                    caffeMenu.add(
                        CaffeMenu(
                            idMenu = it.idMenu!!,
                            category = it.category!!,
                            menuCaffeName = it.menuCaffeName!!,
                            price = it.price!!,
                            description = it.description!!,
                            photo = it.photo!!,


                        )
                    )
                }

                emit(BaseResult.Success(caffeMenu))
            } else {
                emit(BaseResult.Error(response.message))
            }
        }
    }

    override suspend fun getAllMenu(): Flow<BaseResult<List<CaffeMenu>, String>> {
        return flow {
            val response = apiService.fetchAllMenu()
            val caffeMenu = arrayListOf<CaffeMenu>()
            if (!response.data.isNullOrEmpty()) {
                response.data.forEach {
                    caffeMenu.add(
                        CaffeMenu(
                            idMenu = it.idMenu!!,
                            category = it.category!!,
                            menuCaffeName = it.menuCaffeName!!,
                            price = it.price!!,
                            description = it.description!!,
                            photo = it.photo!!,


                            )
                    )
                }

                emit(BaseResult.Success(caffeMenu))
            } else {
                emit(BaseResult.Error(response.message))
            }
        }
    }
}