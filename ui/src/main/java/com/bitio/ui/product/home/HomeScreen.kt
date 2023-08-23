package com.bitio.ui.product.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bitio.productscomponent.domain.entities.Brand
import com.bitio.productscomponent.domain.entities.products.CollectionGroup
import com.bitio.productscomponent.domain.entities.products.ProductWithOffer
import com.bitio.ui.product.details.navigateToProductDetailsScreen
import com.bitio.ui.product.home.composables.BrandRow
import com.bitio.ui.product.home.composables.CategoriesRow
import com.bitio.ui.product.home.composables.CollectionPager
import com.bitio.ui.product.home.composables.OffersPager
import com.bitio.ui.product.home.composables.myImage
import com.bitio.ui.product.home.offers.navigateToOffersScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        CollectionPager(collectionGroups = List(5) { collection })
        CategoriesRow()

        OffersPager(
            productsWithOffer = List(10) { productWithOffer },
            onSeeAllClicked = { },
            onAddToCartClicked = {},
            onAddToFavoriteClicked = {},
            onClickProduct = {}
        )
        OffersPager(
            productsWithOffer = List(10) { productWithOffer },
            onSeeAllClicked = navController::navigateToOffersScreen,
            onAddToCartClicked = {},
            onAddToFavoriteClicked = {},
            onClickProduct = navController::navigateToProductDetailsScreen
        )


        BrandRow(
            brand = brand,
            products = List(20) { productWithOffer },
            onSeeAllClicked = {},
            onCardClicked = {},
            onAddToFavoriteClicked = {},
            onAddToCartClicked = {}
        )


        BrandRow(
            brand = brand,
            products = List(20) { productWithOffer },
            onSeeAllClicked = {},
            onCardClicked = {},
            onAddToFavoriteClicked = {},
            onAddToCartClicked = {}
        )


        BrandRow(
            brand = brand,
            products = List(20) { productWithOffer },
            onSeeAllClicked = {},
            onCardClicked = {},
            onAddToFavoriteClicked = {},
            onAddToCartClicked = {}
        )


        BrandRow(
            brand = brand,
            products = List(20) { productWithOffer },
            onSeeAllClicked = {},
            onCardClicked = {},
            onAddToFavoriteClicked = {},
            onAddToCartClicked = {}
        )


    }
}

val collection = object : CollectionGroup {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Summer COLLECTION"
    override val image: String
        get() = myImage

    override val description: String
        get() = "For Selected collection"
    override val saleRatio: Float
        get() = 0.4f
}
val productWithOffer = object : ProductWithOffer {
    override val oldPrice: Float
        get() = 200f
    override val id: String
        get() = "1"
    override val title: String
        get() = "idk"
    override val image: String
        get() = myImage
    override val price: Float
        get() = 150f

}
val brand = object : Brand {
    override val id: String
        get() = ""
    override val name: String
        get() = "Any"
    override val description: String
        get() = "any"
    override val image: String
        get() = myImage
}


