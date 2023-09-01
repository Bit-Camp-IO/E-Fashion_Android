package com.bitio.ui.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.product.details.composables.DetailsCard
import com.bitio.ui.product.details.composables.ImagesPager
import com.bitio.ui.product.models.UiProductDetails
import com.bitio.ui.shared.screenState.ErrorScreen
import com.bitio.ui.shared.screenState.LoadingScreen
import com.bitio.ui.shared.screenState.UiDataState2
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(navController: NavController) {
    // val productDetails = productDetails

    val viewModel = koinViewModel<DetailsViewModel>()


    when (val screenState = viewModel.screenState) {
        is UiDataState2.Error -> ErrorScreen(message = screenState.message)
        is UiDataState2.Loading -> LoadingScreen()
        is UiDataState2.Success -> DetailsScreenContent(screenState.data)
    }


}

@Composable
fun DetailsScreenContent(productDetails: UiProductDetails) {
    val scrollState = rememberScrollState()
    Column(
        Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy((-64).dp)
    ) {
        ImagesPager(
            modifier = Modifier.graphicsLayer {
                translationY = 0.5f * scrollState.value
                alpha = 1 - (0.75f * scrollState.value.toFloat() / scrollState.maxValue)
            },
            images = productDetails.images
        )
        DetailsCard(productDetails = productDetails)

    }
}


const val myImage =
    "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"