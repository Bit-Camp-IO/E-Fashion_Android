package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.productscomponent.domain.entities.selectable.ColorOption

@Stable
data class UiProductDetails(
    override val id: String,
    override val name: String,
    override val images: List<String>,
    override val price: Float,
    override val oldPrice: Float,
    override val discount: Float,
    override val description: String,
    override val brandName: String,
    override val isAvailable: Boolean,
    override val isNew: Boolean,
    override val rate: Float,
    override val colors: List<ColorOption>,
    override val sizes: List<String>,
    val isFavoriteState: MutableState<Boolean>
) : ProductDetails

fun ProductDetails.toUiProductDetails(isFavorite: Boolean): UiProductDetails {
    return UiProductDetails(
        id, name, images, price,
        oldPrice, discount,
        description, brandName,
        isAvailable, isNew,
        rate, colors, sizes,
        isFavoriteState = mutableStateOf(isFavorite)
    )
}