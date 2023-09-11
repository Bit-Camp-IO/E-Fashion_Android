package com.bitio.ui.product.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.productscomponent.domain.useCase.GetProductDetailsUseCase
import com.bitio.ui.product.models.UiProductDetails
import com.bitio.ui.product.models.toUiProductDetails
import com.bitio.ui.shared.screenState.UiDataState2
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {
    private val productId: String = checkNotNull(savedStateHandle[DetailsArgsRoute.PRODUCT_ID])
    var screenState: UiDataState2<UiProductDetails> by mutableStateOf(UiDataState2.Loading())

    init { updateProductDetails() }
    private fun updateProductDetails() {
        viewModelScope.launch {
            val data = getProductDetailsUseCase(productId)
            data.onSuccess {
                screenState = UiDataState2.Success(it.toUiProductDetails(false))

            }
            data.onFailure {
                screenState = UiDataState2.Error(it.message)
            }
        }
    }
}

