package com.bitio.ui.product.productsList.largeCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.Product
import com.bitio.ui.R
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.product.firstItemLargeShape
import com.bitio.ui.product.lastItemLargeShape
import com.bitio.ui.product.middleItemsLargeShape
import com.bitio.ui.shared.AsyncDescribedImage
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer4Dp


@Composable
fun ProductFirstParallelogramCard(
    product: Product ,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {}
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,
        onAddToCartClicked = onAddToCartClicked,
        onAddToFavClicked = onAddToFavClicked,
        favoriteIconTopPadding = 16.dp,
        shape = firstItemLargeShape()

    )
}


@Composable
fun ProductMiddleParallelogramCard(
    product: Product ,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {}
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,
        onAddToCartClicked = onAddToCartClicked,
        onAddToFavClicked = onAddToFavClicked,
        favoriteIconTopPadding = 50.dp,
        shape = middleItemsLargeShape()

    )
}

@Composable
fun ProductLastParallelogramCard(
    product: Product ,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {}
) {
    ProductLargeParallelogramCard(
        product = product,
        onCardClicked = onCardClicked,
        onAddToCartClicked = onAddToCartClicked,
        onAddToFavClicked = onAddToFavClicked,
        favoriteIconTopPadding = 50.dp,
        shape = lastItemLargeShape()

    )
}



@Composable
fun ProductLargeParallelogramCard(
    product: Product ,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape:Shape
) {
    Box(
        modifier = Modifier
            .size(312.dp, 300.dp)
            .clip(shape)
            .clickable { onCardClicked(product.id) }
    ) {
        AsyncDescribedImage(modifier = Modifier.fillMaxSize(), imageLink = product.image)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            val x = remember { mutableStateOf(true) }
            FavoriteIconButtonCircularBg(
                isFavoriteState = x,
                modifier = Modifier.padding(top = favoriteIconTopPadding, end = 16.dp)
            ) { onAddToFavClicked(product.id) }
            ProductParallelogramDetailsCurve(product) { onAddToCartClicked(product.id) }
        }
    }

}


@Composable
fun ProductParallelogramDetailsCurve(
    product: Product,
    onAddToCartClicked: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.large_shape_curve),
            contentDescription = null
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
                Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                VerticalSpacer4Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            CartIconButton(
                modifier = Modifier.padding(
                    top = 20.dp,
                    end = 16.dp
                )
            ) { onAddToCartClicked() }
        }
    }
}