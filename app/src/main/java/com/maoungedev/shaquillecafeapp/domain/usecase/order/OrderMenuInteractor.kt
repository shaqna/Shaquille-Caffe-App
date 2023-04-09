package com.maoungedev.shaquillecafeapp.domain.usecase.order

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.repository.OrderMenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderMenuInteractor @Inject constructor(
    private val repository: OrderMenuRepository
): OrderMenuUseCase {

    override suspend fun getAllMenu(): Flow<BaseResult<List<CaffeMenu>, String>> {
        return repository.getAllCaffeMenu()
    }

    override suspend fun getOrders(): Flow<BaseResult<List<OrderMenu>, String>> {
        return repository.getOrders()
    }

    override suspend fun addOrder(menuId: String, tableNumber: String): Flow<String> {
        return repository.addOrder(menuId, tableNumber)
    }

    override suspend fun updateOrder(): Flow<String> {
        return repository.updateOrder()
    }

    override suspend fun deleteOrder(orderId: String): Flow<String> {
        return repository.deleteOrder(orderId)
    }
}