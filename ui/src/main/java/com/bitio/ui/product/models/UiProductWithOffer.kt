package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import com.bitio.productscomponent.domain.model.products.ProductWithOffer

data class UiProductWithOffer(
    override val id: String,
    override val title: String,
    override val image: String,
    override val price: Float,
    override val oldPrice: Float,
    val isFavorite: MutableState<Boolean>,
    val onFavoriteIconClicked: (UiProductWithOffer) -> Unit,
    val onAddToCartClicked: (UiProductWithOffer) -> Unit, override val discount: Float
) : ProductWithOffer