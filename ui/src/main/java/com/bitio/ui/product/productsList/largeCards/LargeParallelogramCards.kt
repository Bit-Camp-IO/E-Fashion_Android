package com.bitio.ui.product.productsList.largeCards

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.ui.R
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.productsList.firstItemLargeShape
import com.bitio.ui.product.productsList.lastItemLargeShape
import com.bitio.ui.product.productsList.middleItemsLargeShape
import com.bitio.ui.shared.AsyncDescribedImage
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer4Dp

@Composable
fun ProductParallelogramCardStrategy(
    index: Int, product: UiProduct,
    onCardClicked: (String) -> Unit = {},
) {
    if (index == 0) ProductFirstParallelogramCard(product, onCardClicked)
    else ProductMiddleParallelogramCard(product, onCardClicked)
}

@Composable
fun ProductFirstParallelogramCard(
    product: UiProduct,
    onCardClicked: (String) -> Unit = {},
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,
        favoriteIconTopPadding = 16.dp,
        shape = firstItemLargeShape

    )
}


@Composable
fun ProductMiddleParallelogramCard(
    product: UiProduct,
    onCardClicked: (String) -> Unit = {},
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,
        favoriteIconTopPadding = 50.dp,
        shape = middleItemsLargeShape

    )
}

@Composable
fun ProductLastParallelogramCard(
    product: UiProduct,
    onCardClicked: (String) -> Unit = {},
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,


        favoriteIconTopPadding = 50.dp,
        shape = lastItemLargeShape

    )
}


@Composable
fun ProductLargeParallelogramCard(
    product: UiProduct,
    onCardClicked: (String) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape: Shape
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(LargeCardAspectRatio)
            .clip(shape).clipToBounds()
            .clickable { onCardClicked(product.id) }

    ) {
        AsyncDescribedImage(modifier = Modifier.fillMaxSize(), imageLink = product.image)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {

            FavoriteIconButtonCircularBg(
                isFavoriteState = product.isFavoriteState,
                modifier = Modifier.padding(top = favoriteIconTopPadding, end = 16.dp),
                productId = product.id
            ) { product.onAddToFavoriteClicked(product) }
            ProductParallelogramDetailsCurve(product) { product.onAddToCartClicked(product) }
        }
    }

}


@Composable
fun ProductParallelogramDetailsCurve(
    product: Product,
    onAddToCartClicked: (String) -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.7f),
            painter = painterResource(id = R.drawable.large_shape_curve),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 46.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalSpacer24Dp()
            Column {
                Text(text = product.title, style = MaterialTheme.typography.titleLarge)
                VerticalSpacer4Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            CartIconButton(
                modifier = Modifier.padding(top = 20.dp, end = 16.dp),
                productId = product.id,
                onAddToCartClicked
            )
        }
    }
}