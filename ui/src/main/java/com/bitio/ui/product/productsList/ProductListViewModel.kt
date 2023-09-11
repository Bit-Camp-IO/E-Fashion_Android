package com.bitio.ui.product.productsList

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.bitio.productscomponent.domain.useCase.AddOrRemoveProductFromFavorite
import com.bitio.productscomponent.domain.useCase.GetFavoriteIdsUseCase
import com.bitio.productscomponent.domain.useCase.GetProductsByBrandAndCategoryUseCase
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.models.toUiProduct
import com.bitio.ui.shared.GenericPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProductListViewModel(
    private val getProductsByBrandAndCategoryUseCase: GetProductsByBrandAndCategoryUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
    private val addOrRemoveProductFromFavorite: AddOrRemoveProductFromFavorite
) : ViewModel() {
    private var listOfCachedData = mutableListOf<UiProduct>()
    private val listId = ""
    private val categories = listOf<String>()
    private val hasDiscount = false

    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        GenericPagingSource(::configureData)
    }.flow.map {
        it.map {
            listOfCachedData.add(it); Log.d(
            "gggg",
            listOfCachedData.size.toString()
        );it
        }
    }.cachedIn(viewModelScope)

    init {
        observeFavorites()
    }

    private suspend fun configureData(pageIndex: Int): Result<List<UiProduct>> {
        return getProductsByBrandAndCategoryUseCase.invoke(
            brand = listId,
            categories,
            hasDiscount,
            pageIndex,
            20
        ).map { it.map { it.toUiProduct(false, {}, ::onAddToFavoriteClicked) } }
    }

    private suspend fun test(pageIndex: Int): Result<List<UiProduct>> {

        return Result.success(List(20) {
            UiProduct(
                id = "64efcf9d5aac1e64115155b6",
                title = "ClassicClassicClassicClassicClassicClassicClassicClassicClassic",
                image="https://fastly.picsum.photos/id/309/200/300",
                price = 0f,
                oldPrice = 0f,
                discount = 0f,
                isFavoriteState = mutableStateOf(getFavoriteIdsUseCase().value.contains("64efcf9d5aac1e64115155b6")),
                onAddToFavoriteClicked=::onAddToFavoriteClicked,
                onAddToCartClicked  ={}

            )
        })
    }

    private fun observeFavorites() {
        viewModelScope.launch(Dispatchers.Default) {
            getFavoriteIdsUseCase().collect {
                upDateProductFlow(it)
            }
        }
    }

    private fun onAddToFavoriteClicked(uiProduct: UiProduct) {
        viewModelScope.launch {
            addOrRemoveProductFromFavorite(uiProduct, uiProduct.isFavoriteState.value)
            // uiProduct.isFavoriteState.value = !uiProduct.isFavoriteState.value
        }
    }

    private suspend fun upDateProductFlow(favoriteIds: HashSet<String>) {
        listOfCachedData.forEach {
            viewModelScope.launch {
                val isFavorite = favoriteIds.contains(it.id)
                it.updateFavoriteState(isFavorite)
            }

        }

    }


}

