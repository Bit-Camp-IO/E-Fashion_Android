package com.bitio.ui.product.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.entities.Brand
import com.bitio.productscomponent.domain.entities.products.CollectionGroup
import com.bitio.productscomponent.domain.useCase.AddOrRemoveProductFromFavorite
import com.bitio.productscomponent.domain.useCase.GetBrandsUseCase
import com.bitio.productscomponent.domain.useCase.GetCategoryByGenderUseCase
import com.bitio.productscomponent.domain.useCase.GetFavoriteIdsUseCase
import com.bitio.productscomponent.domain.useCase.GetProductsByBrandAndCategoryUseCase
import com.bitio.ui.product.models.UiCategory
import com.bitio.ui.product.models.stateHolders.CategoryStateHolder
import com.bitio.ui.product.models.stateHolders.ProductStateHolder
import com.bitio.ui.shared.screenState.UiDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getProductsByBrandAndCategoryUseCase: GetProductsByBrandAndCategoryUseCase,
    private val addOrRemoveProductFromFavorite: AddOrRemoveProductFromFavorite,
    private val getCategoryByGenderUseCase: GetCategoryByGenderUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
    private val getBrandsUseCase: GetBrandsUseCase
) : ViewModel() {

    var uiDataState: UiDataState by mutableStateOf(UiDataState.Loading)
        private set

    private val favoriteIdFlow: StateFlow<HashSet<String>> = getFavoriteIdsUseCase()
    val productStateHoldersFlow = MutableStateFlow<List<ProductStateHolder>>(listOf())


     val offersStateHolder = ProductStateHolder(
        null,
        true,
        getProductsByBrandAndCategoryUseCase,
        addOrRemoveProductFromFavorite,
        viewModelScope,
        favoriteIdFlow,
        ::updateUiDataState
    )
    private val categoryStateHolder =
        CategoryStateHolder(viewModelScope, getCategoryByGenderUseCase, ::updateUiDataState)

    val categoriesFlow: StateFlow<List<UiCategory>> = categoryStateHolder.categoryFlow
    val booleanState = categoryStateHolder.gendersState

    private val _collections = mutableStateOf<List<CollectionGroup>>(listOf())
    val collections: State<List<CollectionGroup>>
        get() = _collections


    init {

        updateCategories()
        getHomePayload()
    }

    private fun getHomePayload() {
        viewModelScope.launch {
            val brands = getBrandsUseCase()
            brands.onFailure {
                updateUiDataState(UiDataState.Error(it.message))

                return@launch
            }
            brands.onSuccess {
                val productStateHolders = it.map { productStateHolderFactory(it) }
                productStateHoldersFlow.value = productStateHolders

            }

        }
    }

    private fun productStateHolderFactory(brand: Brand): ProductStateHolder {
        return ProductStateHolder(
            brand = brand,
            getProductsByBrandAndCategoryUseCase = getProductsByBrandAndCategoryUseCase,
            addOrRemoveProductFromFavorite = addOrRemoveProductFromFavorite,
            coroutineScope = viewModelScope,
            favoriteIds = favoriteIdFlow,
            updateUiDataState = ::updateUiDataState
        )
    }

    private fun updateCategories() {
        viewModelScope.launch {
            val x = getBrandsUseCase()
        }

    }

    private fun updateCollections() {}

//    private fun updateProducts() {
//        productStateHolder.initProducts()
//    }

    fun applyFilters() {
        val selectedCategories = categoryStateHolder.getSelectedCategories()

        offersStateHolder.setNewCategories(selectedCategories)
        productStateHoldersFlow.value.forEach{it.setNewCategories(selectedCategories)}
    }

    private suspend fun updateUiDataState(state: UiDataState) {
        withContext(Dispatchers.Main) {
            uiDataState = state
        }
    }

    fun changeGenderState(index: Int) {

        categoryStateHolder.changeGenderState(index)}
}