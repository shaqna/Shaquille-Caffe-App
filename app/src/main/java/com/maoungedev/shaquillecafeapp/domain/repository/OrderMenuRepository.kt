package com.maoungedev.shaquillecafeapp.domain.repository

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import kotlinx.coroutines.flow.Flow

interface OrderMenuRepository {

    suspend fun getAllCaffeMenu(): Flow<BaseResult<List<CaffeMenu>, String>>
    suspend fun getOrders(): Flow<BaseResult<List<OrderMenu>, String>>
    suspend fun addOrder(menuId: String, tableNumber: String): Flow<String>
    suspend fun updateOrder(): Flow<String>
    suspend fun deleteOrder(orderId: String): Flow<String>

}