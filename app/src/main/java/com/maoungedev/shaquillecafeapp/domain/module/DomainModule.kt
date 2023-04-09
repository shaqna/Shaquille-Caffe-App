package com.maoungedev.shaquillecafeapp.domain.module

import com.maoungedev.shaquillecafeapp.domain.repository.CaffeMenuRepository
import com.maoungedev.shaquillecafeapp.domain.repository.KitchenRepository
import com.maoungedev.shaquillecafeapp.domain.repository.OrderMenuRepository
import com.maoungedev.shaquillecafeapp.domain.usecase.caffe.CaffeMenuInteractor
import com.maoungedev.shaquillecafeapp.domain.usecase.caffe.CaffeMenuUseCase
import com.maoungedev.shaquillecafeapp.domain.usecase.kitchen.KitchenInteractor
import com.maoungedev.shaquillecafeapp.domain.usecase.kitchen.KitchenUseCase
import com.maoungedev.shaquillecafeapp.domain.usecase.order.OrderMenuInteractor
import com.maoungedev.shaquillecafeapp.domain.usecase.order.OrderMenuUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideKitchenUseCase(kitchenRepository: KitchenRepository): KitchenUseCase {
        return KitchenInteractor(kitchenRepository)
    }

    @Singleton
    @Provides
    fun provideMenuCaffeUseCase(caffeMenuRepository: CaffeMenuRepository): CaffeMenuUseCase {
        return CaffeMenuInteractor(caffeMenuRepository)
    }

    @Singleton
    @Provides
    fun provideOrderCaffeUseCase(orderMenuRepository: OrderMenuRepository): OrderMenuUseCase {
        return OrderMenuInteractor(orderMenuRepository)
    }

}