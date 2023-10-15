package com.bitio.infrastructure.product.di

import com.bitio.infrastructure.product.local.ProductDaoAdapter
import com.bitio.infrastructure.product.local.room.ProductRoomDao
import com.bitio.infrastructure.product.remote.ProductApiAdapter
import com.bitio.infrastructure.product.remote.retrofit.ProductRetrofit
import com.bitio.infrastructure.product.remote.retrofit.ProductsApiRetrofit
import com.bitio.infrastructure.roomConfiguration.AppDatabase
import com.bitio.productscomponent.data.local.dataSource.ProductDao
import com.bitio.productscomponent.data.remote.ProductsApi
import com.bitio.productscomponent.data.repository.ProductRepositoryImpl
import com.bitio.productscomponent.domain.repository.ProductRepository
import com.bitio.productscomponent.domain.useCase.AddOrRemoveProductFromFavorite
import com.bitio.productscomponent.domain.useCase.GetBrandsUseCase
import com.bitio.productscomponent.domain.useCase.GetCategoryByGenderUseCase
import com.bitio.productscomponent.domain.useCase.GetFavoriteIdsUseCase
import com.bitio.productscomponent.domain.useCase.GetProductByIdUseCase
import com.bitio.productscomponent.domain.useCase.GetProductDetailsUseCase
import com.bitio.productscomponent.domain.useCase.GetProductsByBrandAndCategoryUseCase
import com.bitio.productscomponent.domain.useCase.cart.AddProductToCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.DeleteProductFromCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.EditProductOfCartUseCase
import com.bitio.productscomponent.domain.useCase.cart.GetAllProductsFromCartUseCase
import org.koin.dsl.module


val productKoinModule = module {
    //api with retrofit
    single<ProductsApiRetrofit> { ProductRetrofit.service }
    single<ProductsApi> { ProductApiAdapter(get()) }
    //db with room
    single<ProductRoomDao> { get<AppDatabase>().productRoomDao() }
    single<ProductDao> { ProductDaoAdapter(get()) }
    //repo
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
    //useCases
    factory { GetProductsByBrandAndCategoryUseCase(get()) }
    factory { GetProductByIdUseCase(get()) }
    factory { GetCategoryByGenderUseCase(get()) }
    factory { GetBrandsUseCase(get()) }
    factory { AddOrRemoveProductFromFavorite(get()) }
    factory { GetFavoriteIdsUseCase(get()) }
    factory { GetProductDetailsUseCase(get()) }


    factory { GetAllProductsFromCartUseCase(get()) }
    factory { AddProductToCartUseCase(get()) }
    factory { DeleteProductFromCartUseCase(get()) }
    factory { EditProductOfCartUseCase(get()) }


}