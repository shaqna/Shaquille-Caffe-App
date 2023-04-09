package com.maoungedev.shaquillecafeapp.data.repository

import com.maoungedev.shaquillecafeapp.data.source.service.ApiService
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.repository.OrderMenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): OrderMenuRepository {
    override suspend fun getAllCaffeMenu(): Flow<BaseResult<List<CaffeMenu>, String>> =
        flow {
            val response = apiService.fetchAllMenu()
            val menu = arrayListOf<CaffeMenu>()
            if(!response.data.isNullOrEmpty()) {
                response.data.forEach {
                    menu.add(
                        CaffeMenu(
                            idMenu = it.idMenu!!,
                            menuCaffeName = it.menuCaffeName!!,
                            category = it.category!!,
                            price = it.price!!,
                            photo = it.photo!!,
                            description = it.description!!
                        )
                    )
                }

                emit(BaseResult.Success(menu))
            } else {
                emit(BaseResult.Error(response.message))
            }
        }

    override suspend fun getOrders(): Flow<BaseResult<List<OrderMenu>, String>> =
        flow {
            val response = apiService.fetchOrder()
            val orders = arrayListOf<OrderMenu>()
            if(!response.data.isNullOrEmpty()) {
                response.data.forEach {
                    orders.add(
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
                emit(BaseResult.Success(orders))
            } else {
                emit(BaseResult.Error(response.message))
            }

        }

    override suspend fun addOrder(menuId: String, tableNumber: String): Flow<String> =
        flow {
            val response = apiService.addOrder(menuId, tableNumber)
            if(response.code == "200") {
                emit(response.message)
            } else {
                emit(response.message)
            }
        }

    override suspend fun updateOrder(): Flow<String> =
        flow {
            val response = apiService.updateOrderState()
            if(response.code == "200") {
                emit(response.message)
            } else {
                emit(response.message)
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