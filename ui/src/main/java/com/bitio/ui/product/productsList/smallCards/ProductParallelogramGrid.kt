package com.bitio.ui.product.productsList.smallCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.productscomponent.domain.entities.products.ProductWithOffer


@Composable
fun ProductParallelogramGrid(products: List<ProductWithOffer>, onCardClicked:(Int)->Unit={}) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((-12).dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp)
    ) {
        items(products.size, contentType = { Product::class }, key = { it }) {
             SmallParallelogramCardFactory(products[it], cardIndex = it)

        }
    }
}