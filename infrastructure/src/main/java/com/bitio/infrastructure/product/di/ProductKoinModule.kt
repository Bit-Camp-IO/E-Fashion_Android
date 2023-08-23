package com.bitio.infrastructure.product.di

import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.infrastructure.product.local.ProductDaoAdapter
import com.bitio.infrastructure.product.local.room.ProductRoomDao
import com.bitio.infrastructure.product.remote.ProductRetrofit
import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.data.repository.ProductRepositoryImpl
import com.bitio.productscomponent.domain.repository.ProductRepository
import org.koin.dsl.module


val productKoinModule = module {
    single<ProductsApi> { ProductRetrofit.service }
    single<ProductRoomDao> { get<AppDatabase>().productRoomDao() }
    single<ProductDao> { ProductDaoAdapter(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }


}