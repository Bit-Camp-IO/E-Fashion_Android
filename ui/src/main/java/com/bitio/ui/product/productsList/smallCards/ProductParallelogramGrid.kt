package com.bitio.ui.product.productsList.smallCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bitio.ui.product.models.UiProduct


@Composable
fun ProductParallelogramGrid(lazyProducts: LazyPagingItems<UiProduct>, onCardClicked:(Int)->Unit={}) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((-12).dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp)
    ) {
        items(lazyProducts.itemCount, contentType = { UiProduct::class }, key = { it }) {
             SmallParallelogramCardFactory(lazyProducts[it]!!, cardIndex = it)

        }
    }
}