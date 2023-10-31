package com.bitio.ui.product.models.stateHolders

import androidx.compose.runtime.Stable
import com.bitio.productscomponent.domain.model.Brand
import com.bitio.productscomponent.domain.model.products.Product
import com.bitio.productscomponent.domain.useCase.AddOrRemoveProductFromFavorite
import com.bitio.productscomponent.domain.useCase.GetProductsByBrandAndCategoryUseCase
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.models.toUiProduct
import com.bitio.ui.shared.screenState.UiDataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductStateHolder(
    val brand: Brand?,
    private val offersOnly: Boolean = false,
    private val getProductsByBrandAndCategoryUseCase: GetProductsByBrandAndCategoryUseCase,
    private val addOrRemoveProductFromFavorite: AddOrRemoveProductFromFavorite,
    private val coroutineScope: CoroutineScope,
    private val favoriteIds: StateFlow<HashSet<String>>,
    private val updateUiDataState: suspend (UiDataState) -> Unit
) {

    init {
        initProducts()
        observeFavIds()
    }

    @Stable
    val productsFlow = MutableStateFlow<List<UiProduct>>(listOf())


    private fun initProducts() {
        coroutineScope.launch(Dispatchers.IO) {
            val result = getProductsByBrandAndCategoryUseCase(brand?.id, hasDiscount = offersOnly)
            result.onFailure { updateUiDataState(UiDataState.Error(it.message)) }
            result.onSuccess {
                updateUiDataState(UiDataState.Success)
                convertToUiProducts(products = it)
            }

        }
    }

    private fun convertToUiProducts(products: List<Product>) {
        val uiProducts = products.map {
            it.toUiProduct(
                favoriteIds.value.contains(it.id), ::onAddToCartClicked, ::onAddToFavoriteClicked
            )
        }
        productsFlow.value = uiProducts
    }

    fun setNewCategories(categoriesIds: List<String>) {
        coroutineScope.launch(Dispatchers.IO) {

            val result = getProductsByBrandAndCategoryUseCase(
                brand = brand?.id,
                hasDiscount = offersOnly,
                categoriesIds = categoriesIds,
                page = 1, limit = 20
            )

            result.onSuccess { convertToUiProducts(it) }
            result.onFailure { updateUiDataState(UiDataState.Error(it.message)) }
        }

    }

    private fun onAddToFavoriteClicked(uiProduct: UiProduct) {
        coroutineScope.launch {
            addOrRemoveProductFromFavorite(uiProduct, uiProduct.isFavoriteState.value)
            uiProduct.isFavoriteState.value = !uiProduct.isFavoriteState.value
        }
    }

    private fun onAddToCartClicked(uiProduct: UiProduct) {

    }

    private fun observeFavIds() {
        coroutineScope.launch(Dispatchers.Default) {
            favoriteIds.collect { set ->
//                productsFlow.value.forEach {
//                    val isFavorite = set.contains(it.id)
//                    it.updateFavoriteState(isFavorite)
//                }
            }
        }


    }

    private fun updateProductFavStateIfChanged(favoriteIds: HashSet<String>) {
        productsFlow.value.forEach {
            it.isFavoriteState.value = favoriteIds.contains(it.id)
        }

    }

}