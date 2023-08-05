package com.bitio.ui.product.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.bitio.productscomponent.domain.entities.CollectionGroup
import com.bitio.ui.product.home.composables.CategoriesRow
import com.bitio.ui.product.home.composables.CollectionPager

@Composable
fun HomeScreen() {
    LazyColumn {
        item{
            CollectionPager(collectionGroups = List(5) { collection })
        }
        item { CategoriesRow() }
    }
}

val collection = object : CollectionGroup {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Summer COLLECTION"
    override val image: String
        get() =
            "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"
    override val description: String
        get() = "For Selected collection"
    override val saleRatio: Float
        get() = 0.4f
}









