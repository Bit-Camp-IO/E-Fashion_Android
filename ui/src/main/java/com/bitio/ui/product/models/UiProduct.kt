package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.bitio.productscomponent.domain.model.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Stable
data class UiProduct(
    override val id: String,
    override val title: String,
    override val image: String,
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
    val isFavoriteState: MutableState<Boolean>,
    val onAddToFavoriteClicked: (UiProduct) -> Unit,
    val onAddToCartClicked: (UiProduct) -> Unit
) : Product {
    //thread safe update update for isFavorite
    suspend fun updateFavoriteState(isFavorite: Boolean) {
        withContext(Dispatchers.Main) {
            isFavoriteState.value = isFavorite
        }

    }

}

fun Product.toUiProduct(
    isFavorite: Boolean,
    onAddToCartClicked: (UiProduct) -> Unit,
    onAddToFavoriteClicked: (UiProduct) -> Unit
) = UiProduct(
    id = id,
    title = title,
    image = image,
    price = price,
    oldPrice = oldPrice,
    discount = discount,
    isFavoriteState = mutableStateOf(isFavorite),
    onAddToFavoriteClicked = onAddToFavoriteClicked,
    onAddToCartClicked = onAddToCartClicked

)