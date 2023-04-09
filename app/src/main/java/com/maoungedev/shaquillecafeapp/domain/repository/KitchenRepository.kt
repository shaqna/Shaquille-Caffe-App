package com.maoungedev.shaquillecafeapp.domain.repository

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import kotlinx.coroutines.flow.Flow

interface KitchenRepository {

    suspend fun getAllKitchenQueue(): Flow<BaseResult<List<OrderMenu>, String>>
    suspend fun deleteOrder(orderId: String): Flow<String>
}