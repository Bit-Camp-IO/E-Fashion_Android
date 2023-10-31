package com.bitio.ui.product.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.bitio.productscomponent.domain.model.products.CollectionGroup
import com.bitio.productscomponent.domain.model.products.ProductWithOffer
import com.bitio.ui.product.details.navigateToProductDetailsScreen
import com.bitio.ui.product.home.composables.BrandRow
import com.bitio.ui.product.home.composables.CategoriesRow
import com.bitio.ui.product.home.composables.CollectionPager
import com.bitio.ui.product.home.composables.OffersPager
import com.bitio.ui.product.home.composables.myImage
import com.bitio.ui.product.home.offers.navigateToOffersScreen
import com.bitio.ui.shared.screenState.ErrorScreen
import com.bitio.ui.shared.screenState.LoadingScreen
import com.bitio.ui.shared.screenState.UiDataState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<HomeViewModel>()

    Box {
        HomeScreenContent(
            navController = navController,
            viewModel = viewModel
        )
        when (val dataState = viewModel.uiDataState) {

            is UiDataState.Error -> ErrorScreen(message = dataState.message)
            is UiDataState.Loading -> LoadingScreen()
            is UiDataState.Success -> {}


        }
    }
}


@Composable
fun HomeScreenContent(navController: NavController, viewModel: HomeViewModel) {
    val brandsRows = viewModel.productStateHoldersFlow.collectAsState()
    // Column(modifier = Modifier.verticalScroll(rememberScrollState()))

    LazyColumn {
        item { CollectionPager(collectionGroups = List(5) { collection }) }
        item {
            CategoriesRow(
                viewModel.categoriesFlow,
                viewModel.booleanState,
                viewModel::changeGenderState,
                viewModel::applyFilters
            )
        }
        item {
            val offersStateHolder=viewModel.offersStateHolder
            val products=offersStateHolder.productsFlow.collectAsState().value
            OffersPager(
                productsWithOffer = products,
                onSeeAllClicked = navController::navigateToOffersScreen,
                onClickProduct = navController::navigateToProductDetailsScreen
            )
        }
        items(count = brandsRows.value.size) {
            val stateHolder=brandsRows.value[it]
            val products=stateHolder.productsFlow.collectAsState().value
            BrandRow(
                brand = stateHolder.brand!!,
                products = products,
                onSeeAllClicked ={},// navController::navigateToZaraScreen,
                onCardClicked = navController::navigateToProductDetailsScreen,
                onAddToFavoriteClicked = {},
                onAddToCartClicked = {}
            )
        }


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
    override val discount: Float
        get() = 5f

}

