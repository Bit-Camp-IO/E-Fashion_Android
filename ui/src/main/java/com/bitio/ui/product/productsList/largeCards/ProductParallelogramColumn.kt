package com.bitio.ui.product.productsList.largeCards


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.ui.product.home.productWithOffer

@Composable
fun ProductParallelogramColumn(products: List<Product> = List(10) { productWithOffer }) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy((-24).dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)

    ) {
        item(contentType = Product::class, key = 0) { ProductFirstParallelogramCard() }
        items(count = products.size - 1, contentType = { Product::class }, key = {it+1}) {
            ProductMiddleParallelogramCard()
        }
    }
}