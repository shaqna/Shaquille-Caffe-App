package com.maoungedev.shaquillecafeapp.domain.usecase.kitchen

import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.repository.KitchenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KitchenInteractor @Inject constructor(
    private val repository: KitchenRepository
): KitchenUseCase {
    override suspend fun getAllKitchenQueue(): Flow<BaseResult<List<OrderMenu>, String>> {
        return repository.getAllKitchenQueue()
    }

    override suspend fun deleteOrder(orderId: String): Flow<String> {
        return  repository.deleteOrder(orderId)
    }
}