@file:Suppress("NOTHING_TO_INLINE")

package com.bitio.ui.product.productsList.smallCards

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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.productsList.firstLeftShape
import com.bitio.ui.product.productsList.firstRightShape
import com.bitio.ui.product.productsList.middleLeftShape
import com.bitio.ui.product.productsList.middleRightShape
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer4Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Stable
@Composable
inline fun SmallParallelogramCardFactory(
    product: UiProduct,
    noinline onCardClicked: (Int) -> Unit = {},
    noinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    cardIndex: Int
) {
    val leftPainter= painterResource(id = R.drawable.small_curve_left)
    val rightPainter= painterResource(id = R.drawable.small_curve_right)
    when {
        cardIndex == 0 -> FirstLeftParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked,
            leftPainter
        )

        cardIndex == 1 -> FirstRightParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked,
            rightPainter
        )

        cardIndex % 2 == 0 -> MiddleLeftParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked,
            leftPainter
        )

        else -> MiddleRightParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked,
            rightPainter
        )

    }

}

@Stable
@Composable
inline fun FirstLeftParallelogramCards(
    product: UiProduct,
    crossinline onCardClicked: (Int) -> Unit = {},
    crossinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    painter: Painter
) {

    SmallLeftParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        8.dp,
        firstLeftShape,
        painter
    )
}

@Stable
@Composable
inline fun FirstRightParallelogramCards(
    product: UiProduct,
    noinline onCardClicked: (Int) -> Unit = {},
    noinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    rightPainter: Painter,
) {
    SmallRightParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        8.dp,
        firstRightShape
        ,rightPainter
    )
}

@Stable
@Composable
inline fun MiddleLeftParallelogramCards(
    product: UiProduct,
    crossinline onCardClicked: (Int) -> Unit = {},
    crossinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    leftPainter: Painter,
) {
    SmallLeftParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        24.dp,
        middleLeftShape,
        leftPainter
    )
}

@Stable
@Composable
inline fun MiddleRightParallelogramCards(
    product: UiProduct,
    noinline onCardClicked: (Int) -> Unit = {},
    noinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    rightPainter: Painter,
) {
    SmallRightParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        24.dp,
        middleRightShape,
        rightPainter
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Stable
@Composable
inline fun SmallLeftParallelogramCards(
    product: UiProduct,
    crossinline onCardClicked: (Int) -> Unit = {},
    crossinline onAddToCartClicked: (Int) -> Unit = {},
    noinline onAddToFavClicked: (Int) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape: Shape,
    painter: Painter
) {

    Box(
        modifier = Modifier
            .size(150.dp, 200.dp)
            .clip(shape)
            .clickable { onCardClicked(product.id) }
    ) {
//        AsyncDescribedImage(
//            modifier = Modifier.fillMaxSize(),
//            imageLink = product.image,
//            contentScale = ContentScale.Crop
//        )
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            val x = remember { mutableStateOf(true) }
            FavoriteIconButtonCircularBg(
                isFavoriteState = x,
                productId = product.id,
                modifier = Modifier.padding(top = favoriteIconTopPadding, start = 8.dp),
                onClick = onAddToFavClicked
            )
            LeftParallelogramCurve(product,painter) { onAddToCartClicked(product.id) }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Stable
@Composable
fun SmallRightParallelogramCards(
    product: UiProduct,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape: Shape,
    painter: Painter
) {

    Box(
        modifier = Modifier
            .size(150.dp, 200.dp)
            .clip(shape)
            .clickable { onCardClicked(product.id) }
    ) {
//        AsyncDescribedImage(
//            modifier = Modifier.fillMaxSize(),
//            imageLink = product.image,
//            contentScale = ContentScale.Crop
//        )
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            val x = remember { mutableStateOf(true) }
            FavoriteIconButtonCircularBg(
                isFavoriteState = x,
                productId = product.id,
                modifier = Modifier.padding(top = favoriteIconTopPadding, end = 16.dp),
                onClick = onAddToFavClicked
            )
            RightParallelogramCurve(product,painter) { onAddToCartClicked(product.id) }
        }
    }

}

@Stable
@Composable
fun LeftParallelogramCurve(
    product: UiProduct,
    painter: Painter,
    onAddToCartClicked: (Int) -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartIconButton(
                modifier = Modifier.padding(
                    top = 20.dp,
                    start = 16.dp
                ),
                productId = product.id,
                onAddToCartClicked
            )
            Column {
                Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                VerticalSpacer4Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            HorizontalSpacer24Dp()
        }
    }
}

@Stable
@Composable
fun RightParallelogramCurve(
    product: UiProduct,
    painter: Painter,
    onAddToCartClicked: (Int) -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                ,
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalSpacer24Dp()
            Column {
                Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                VerticalSpacer4Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            CartIconButton(
                modifier = Modifier.padding(
                    top = 20.dp,
                    end = 16.dp
                ),
                productId = product.id,
                onAddToCartClicked
            )


        }
    }
}

