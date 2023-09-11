package com.bitio.ui.product.productsList.largeCards


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.shared.screenState.ErrorScreen
import com.bitio.ui.shared.screenState.LoadingScreen

//this values shall be changed in case i want extraPadding or modify aspect ratio
const val LargeCardHorizontalPadding = 24
const val LargeCardAspectRatio = 1.1F

@Composable
fun ProductParallelogramColumn(lazyProducts: LazyPagingItems<UiProduct>) {
    //this is kinda of master piece pls don't touch it
    val actualPadding = 8.dp
    val horizontalPadding = LargeCardHorizontalPadding.dp
    val dpiValue = LocalDensity.current.density
    var parentSize by remember { mutableStateOf(0.dp) }
    val wrapperBoxHeight = (parentSize - horizontalPadding * 2) / LargeCardAspectRatio
    val extraPadding =
        wrapperBoxHeight / 8 //8 represent the ratio of the slope peek to the whole height


    when (val state = lazyProducts.loadState.refresh) {
        is LoadState.Error -> ErrorScreen(message = state.error.message ?: "UnknownError")
        LoadState.Loading -> LoadingScreen()
        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { parentSize = (it.width.toFloat() / dpiValue).dp }
                    .padding(horizontal = horizontalPadding),
                verticalArrangement = Arrangement.spacedBy((-extraPadding + actualPadding)),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = horizontalPadding)

            ) {


                items(
                    count = lazyProducts.itemCount,
                    contentType = { UiProduct::class },
                    key = { it }) {
                    ProductParallelogramCardStrategy(it, lazyProducts[it]!!)
                }
            }
        }
    }

}



