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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.Product

import com.bitio.ui.R
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.product.productsList.firstLeftShape
import com.bitio.ui.product.productsList.firstRightShape
import com.bitio.ui.product.productsList.middleLeftShape
import com.bitio.ui.product.productsList.middleRightShape
import com.bitio.ui.shared.AsyncDescribedImage
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer4Dp


@Composable
 fun SmallParallelogramCardFactory(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    cardIndex: Int
) {
    when {
        cardIndex == 0 -> FirstLeftParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked
        )

        cardIndex == 1 -> FirstRightParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked
        )

        cardIndex % 2 ==0 -> MiddleLeftParallelogramCards(
            product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked
        )
        else -> MiddleRightParallelogramCards( product,
            onCardClicked,
            onAddToCartClicked,
            onAddToFavClicked)

    }

}


@Composable
fun FirstLeftParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
) {
    SmallLeftParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        8.dp,
        firstLeftShape(),
    )
}

@Composable
fun FirstRightParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
) {
    SmallRightParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        8.dp,
        firstRightShape(),
    )
}

@Composable
fun MiddleLeftParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
) {
    SmallLeftParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        24.dp,
        middleLeftShape(),
    )
}

@Composable
fun MiddleRightParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
) {
    SmallRightParallelogramCards(
        product,
        onCardClicked,
        onAddToCartClicked,
        onAddToFavClicked,
        24.dp,
        middleRightShape(),
    )
}


@Composable
fun SmallLeftParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape: Shape
) {
    Box(
        modifier = Modifier
            .size(150.dp, 200.dp)
            .clip(shape)
            .clickable { onCardClicked(product.id) }
    ) {
        AsyncDescribedImage(modifier = Modifier.fillMaxSize(), imageLink = product.image)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            val x = remember { mutableStateOf(true) }
            FavoriteIconButtonCircularBg(
                isFavoriteState = x,
                modifier = Modifier.padding(top = favoriteIconTopPadding, start = 8.dp)
            ) { onAddToFavClicked(product.id) }
            LeftParallelogramCurve(product) { onAddToCartClicked(product.id) }
        }
    }
}

@Composable
fun SmallRightParallelogramCards(
    product: Product,
    onCardClicked: (Int) -> Unit = {},
    onAddToCartClicked: (Int) -> Unit = {},
    onAddToFavClicked: (Int) -> Unit = {},
    favoriteIconTopPadding: Dp,
    shape: Shape
) {
    Box(
        modifier = Modifier
            .size(150.dp, 200.dp)
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
            RightParallelogramCurve(product) { onAddToCartClicked(product.id) }
        }
    }

}

@Composable
fun LeftParallelogramCurve(
    product: Product,
    onAddToCartClicked: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.small_curve_left),
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
                )
            ) { onAddToCartClicked() }
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

@Composable
fun RightParallelogramCurve(
    product: Product,
    onAddToCartClicked: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.small_curve_right),
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
                )
            ) { onAddToCartClicked() }


        }
    }
}