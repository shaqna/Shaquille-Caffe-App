package com.maoungedev.shaquillecafeapp.data.module

import com.maoungedev.shaquillecafeapp.data.repository.CaffeMenuRepositoryImpl
import com.maoungedev.shaquillecafeapp.data.repository.KitchenRepositoryImpl
import com.maoungedev.shaquillecafeapp.data.repository.OrderRepositoryImpl
import com.maoungedev.shaquillecafeapp.data.source.service.ApiService
import com.maoungedev.shaquillecafeapp.domain.repository.CaffeMenuRepository
import com.maoungedev.shaquillecafeapp.domain.repository.KitchenRepository
import com.maoungedev.shaquillecafeapp.domain.repository.OrderMenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideKitchenRepository(service: ApiService): KitchenRepository {
        return KitchenRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideMenuCaffeRepository(service: ApiService): CaffeMenuRepository {
        return CaffeMenuRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideOrderMenuRepository(service: ApiService) : OrderMenuRepository {
        return OrderRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}