package com.bitio.ui.product.productsList.smallCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.product.models.UiProduct
import com.bitio.ui.product.productsList.firstLeftShape
import com.bitio.ui.product.productsList.firstRightShape
import com.bitio.ui.product.productsList.leftCurve
import com.bitio.ui.product.productsList.middleLeftShape
import com.bitio.ui.product.productsList.middleRightShape
import com.bitio.ui.product.productsList.rightCurve
import com.bitio.ui.shared.AsyncDescribedImage
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer4Dp

@Stable
@Composable
fun SmallParallelogramCardFactory(
    product: UiProduct,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    cardIndex: Int
) {


    when {
        cardIndex == 0 -> SmallParallelogramCard(
            product = product,
            onCardClicked = onCardClicked,
            onAddToCartClicked = onAddToCartClicked,
            onAddToFavClicked = onAddToFavClicked,
            isLeft = true,
            isFirst = true
        )

        cardIndex == 1 -> SmallParallelogramCard(
            product = product,
            onCardClicked = onCardClicked,
            onAddToCartClicked = onAddToCartClicked,
            onAddToFavClicked = onAddToFavClicked,
            isLeft = false,
            isFirst = true

        )

        cardIndex % 2 == 0 -> SmallParallelogramCard(
            product = product,
            onCardClicked = onCardClicked,
            onAddToCartClicked = onAddToCartClicked,
            onAddToFavClicked = onAddToFavClicked,
            isLeft = true,
            isFirst = false,

            )

        else -> SmallParallelogramCard(
            product = product,
            onCardClicked = onCardClicked,
            onAddToCartClicked = onAddToCartClicked,
            onAddToFavClicked = onAddToFavClicked,
            isLeft = false,
            isFirst = false,

            )

    }

}

@Stable
@Composable
fun SmallParallelogramCard(
    product: UiProduct,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    isLeft: Boolean,
    isFirst: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .clip(shapeFactory(isLeft, isFirst))
            .clickable { onCardClicked(product.id) }
    ) {
        AsyncDescribedImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = product.image,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = if (isLeft) Alignment.Start else Alignment.End
        ) {
            val x = remember { mutableStateOf(true) }
            FavoriteIconButtonCircularBg(
                isFavoriteState = x,
                productId = product.id,
                modifier = Modifier
                    .padding(top = (if (isFirst) 8 else 24).dp)
                    .padding(horizontal = 16.dp),
                onClick = onAddToFavClicked
            )
            ParallelogramCurve(product, isLeft) { onAddToCartClicked(product.id) }
        }
    }

}

@Stable
@Composable
fun ParallelogramCurve(
    product: UiProduct,
    isLeft: Boolean,
    onAddToCartClicked: (Int) -> Unit
) {

    Box(
        contentAlignment = Alignment.BottomCenter, modifier = Modifier
            .height(73.dp)
            .fillMaxWidth()
            .graphicsLayer {
                shape = if (isLeft) leftCurve else rightCurve
                clip = true
                translationY = 7 * size.height / 73
            }
            .background(CachedBrushes.getBrush(isLeft))

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLeft) CartIconButton(
                modifier = Modifier.padding(top = 20.dp, end = 16.dp),
                productId = product.id,
                onAddToCartClicked
            ) else HorizontalSpacer24Dp()
            Column {
                Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                VerticalSpacer4Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (isLeft) HorizontalSpacer24Dp() else CartIconButton(
                modifier = Modifier.padding(top = 20.dp, end = 16.dp),
                productId = product.id,
                onAddToCartClicked
            )

        }
    }
}

fun shapeFactory(isLeft: Boolean, isFirst: Boolean): Shape {
    return when {
        !isFirst -> if (isLeft) middleLeftShape else middleRightShape
        else -> if (isLeft) firstLeftShape else firstRightShape

    }
}

object CachedBrushes {
    private val lefTCurveBrush = Brush.linearGradient(
        (0f to Color(0xE6465A6A)),
        (1f to Color(0xE61B2F3F)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    private val rightShapeBrush = Brush.linearGradient(
        (0f to Color(0xE61B2F3F)),
        (1f to Color(0xE6465A6A)),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    fun getBrush(isLeft: Boolean) = if (isLeft) lefTCurveBrush else rightShapeBrush


}



