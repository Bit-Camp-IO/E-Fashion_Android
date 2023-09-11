package com.bitio.ui.product.search

import android.util.Log
import android.util.Range
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.bitio.productscomponent.domain.entities.categories.GenderType
import com.bitio.productscomponent.domain.useCase.AddOrRemoveProductFromFavorite
import com.bitio.productscomponent.domain.useCase.GetCategoryByGenderUseCase
import com.bitio.productscomponent.domain.useCase.GetFavoriteIdsUseCase
import com.bitio.productscomponent.domain.useCase.GetProductsByBrandAndCategoryUseCase
import com.bitio.ui.product.models.UiCategory
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.models.UiSize
import com.bitio.ui.product.models.toUiCategory
import com.bitio.ui.product.models.toUiProduct
import com.bitio.ui.shared.GenericPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchViewModel(
    private val getProductsByBrandAndCategoryUseCase: GetProductsByBrandAndCategoryUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
    private val addOrRemoveProductFromFavorite: AddOrRemoveProductFromFavorite,
    private val getCategoryByGenderUseCase: GetCategoryByGenderUseCase
) : ViewModel() {
    private var listOfCachedData = mutableListOf<UiProduct>()
    private val listId = ""
    private val categoriesIds = listOf<String>()
    private val hasDiscount = false
    val availableSizes =
        listOf("S", "M", "L", "XL", "2XL", "3XL").map { UiSize(it, mutableStateOf(false)) }
    val priceRange = mutableStateOf(0f..100f)
    val x=0f..100f

    val categories = mutableStateOf(listOf<UiCategory>())


    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        GenericPagingSource(::configureData)
    }.flow.map { pagingData ->
        pagingData.map {
            listOfCachedData.add(it); Log.d(
            "gggg",
            listOfCachedData.size.toString()
        );it
        }
    }.cachedIn(viewModelScope)

    init {
        upDateCategories()
    }

    private suspend fun configureData(pageIndex: Int): Result<List<UiProduct>> {
        return getProductsByBrandAndCategoryUseCase.invoke(
            brand = listId,
            categoriesIds,
            hasDiscount,
            pageIndex,
            20
        ).map { it.map { product -> product.toUiProduct(false, {}, ::onAddToFavoriteClicked) } }
    }

    private fun onAddToFavoriteClicked(uiProduct: UiProduct) {
        viewModelScope.launch {
            addOrRemoveProductFromFavorite(uiProduct, uiProduct.isFavoriteState.value)
            // uiProduct.isFavoriteState.value = !uiProduct.isFavoriteState.value
        }
    }

    private fun upDateCategories() {
        viewModelScope.launch {
            val categoriesRes = getCategoryByGenderUseCase.invoke(GenderType.BOTH).getOrThrow()
                .map { it.toUiCategory(false) {} }
            withContext(Dispatchers.IO) {
                categories.value = categoriesRes
            }
        }
    }

}