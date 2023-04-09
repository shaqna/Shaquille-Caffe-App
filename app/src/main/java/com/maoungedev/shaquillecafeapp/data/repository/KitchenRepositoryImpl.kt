package com.maoungedev.shaquillecafeapp.data.repository

import com.maoungedev.shaquillecafeapp.data.source.service.ApiService
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.repository.KitchenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KitchenRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): KitchenRepository {
    override suspend fun getAllKitchenQueue(): Flow<BaseResult<List<OrderMenu>, String>> =
        flow {
            val response = apiService.fetchKitchen()
            val kitchenQueue = arrayListOf<OrderMenu>()
            if(!response.data.isNullOrEmpty()) {
                response.data.forEach {
                    kitchenQueue.add(
                        OrderMenu(
                            orderId = it.orderId!!,
                            tableNumber = it.tableNumber!!,
                            category = it.category!!,
                            menuCaffeName = it.menuCaffeName!!,
                            price = it.price!!,
                            description = it.description!!,
                            photo = it.photo!!,
                            time = it.time!!
                        )
                    )
                }

                emit(BaseResult.Success(kitchenQueue))
            } else {
                emit(BaseResult.Error(response.message))
            }
        }

    override suspend fun deleteOrder(orderId: String): Flow<String> =
        flow {
            val response = apiService.deleteOrderState(orderId)
            if(response.code == "200") {
                emit(response.message)
            } else {
                emit(response.message)
            }
        }
}