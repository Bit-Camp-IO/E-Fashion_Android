package com.bitio.ui.product.productsList.smallCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.ui.product.models.UiProduct


@Composable
fun ProductParallelogramGrid(products: List<UiProduct>,onCardClicked:(Int)->Unit={}) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((12).dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp)
    ) {
        items(products.size, contentType = { Product::class }, key = { it }) {
             SmallParallelogramCardFactory(products[it],cardIndex = it)
//            Box(modifier = Modifier
//                .size(300.dp)
//                .background(Color.White.copy(alpha = 0.5f)).clickable { onCardClicked(products[it].id) })
        }
    }
}