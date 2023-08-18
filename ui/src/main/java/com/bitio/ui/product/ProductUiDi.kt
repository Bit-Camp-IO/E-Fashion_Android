package com.bitio.ui.product

import com.bitio.ui.product.cart.CartViewModel
import com.bitio.ui.product.details.DetailsViewModel
import com.bitio.ui.product.home.HomeViewModel
import com.bitio.ui.product.productsList.ProductListViewModel
import com.bitio.ui.product.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val productUiDi = module {
    viewModel { HomeViewModel() }
    viewModel { DetailsViewModel() }
    viewModel { CartViewModel() }
    viewModel { SearchViewModel() }
    viewModel { ProductListViewModel() }

}