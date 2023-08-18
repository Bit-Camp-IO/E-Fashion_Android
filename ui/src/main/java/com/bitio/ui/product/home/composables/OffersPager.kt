package com.bitio.ui.product.home.composables


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bitio.productscomponent.domain.entities.products.ProductWithOffer
import com.bitio.ui.R
import com.bitio.ui.product.CartIconButtonCircularBg
import com.bitio.ui.product.FavoriteIconButton
import com.bitio.ui.shared.HorizontalSpacer24Dp
import com.bitio.ui.shared.HorizontalSpacer4Dp
import com.bitio.ui.shared.VerticalSpacer8Dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OffersPager(
    productsWithOffer: List<ProductWithOffer>,
    onSeeAllClicked: () -> Unit,
    onAddToCartClicked: (Int) -> Unit,
    onAddToFavoriteClicked: (Int) -> Unit,
    onClickProduct: (Int) -> Unit

) {
    Column {
        ItemsTitleComponent(name = "Offers", onSeeAllClicked)
        VerticalSpacer8Dp()
        val listState = rememberLazyListState()
        val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        val horizontalPadding = ((LocalConfiguration.current.screenWidthDp - 250) / 2).dp

        LazyRow(
            Modifier.height(180.dp),
            flingBehavior = snapFlingBehavior,
            state = listState,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productsWithOffer.size) {
                OfferCard(
                    product = productsWithOffer[it],
                    isInFocusMood = getFocusCardIndex(listState) == it,
                    onAddToCartClicked = onAddToCartClicked,
                    onAddToFavoriteClicked = onAddToFavoriteClicked,
                    onClickProduct = onClickProduct
                )
            }
        }


    }

}

fun getFocusCardIndex(listState: LazyListState): Int {
    return try {
        val start = listState.layoutInfo.viewportStartOffset
        val end = listState.layoutInfo.viewportEndOffset
        val listCenter = (start + end) / 2


        val itemsInfo = listState.layoutInfo.visibleItemsInfo
        val displayedItemsSize = itemsInfo.size
        val candidateItems =
            if (displayedItemsSize % 2 == 0) {
                listOf(itemsInfo[displayedItemsSize / 2 - 1], itemsInfo[displayedItemsSize / 2])
            } else listOf(itemsInfo[displayedItemsSize / 2])
        val focusItem = candidateItems
            .filter { it.offset <= listCenter && it.offset + it.size >= listCenter }

        focusItem[0].index

    } catch (e: IndexOutOfBoundsException) {
        -1
    }

}

@SuppressLint("NewApi")
@Composable
fun OfferCard(
    product: ProductWithOffer,
    isInFocusMood: Boolean,
    onAddToCartClicked: (Int) -> Unit,
    onAddToFavoriteClicked: (Int) -> Unit,
    onClickProduct: (Int) -> Unit
) {
    val width = remember(isInFocusMood) { (if (isInFocusMood) 250 else 220).dp }
    val height = remember(isInFocusMood) { (if (isInFocusMood) 180 else 160).dp }

    Box(
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClickProduct(product.id) },
        contentAlignment = Alignment.BottomEnd
    ) {

        AsyncImage(
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            val x = remember { mutableStateOf(false) }
            FavoriteIconButton(
                Modifier.padding(12.dp),
                isFavoriteState = x
            ) { onAddToFavoriteClicked(product.id) }

            CurveDetailsBar(
                product = product,
                onAddToCartClicked = onAddToCartClicked,

                )
        }

    }
}

@SuppressLint("NewApi")
@Composable

fun CurveDetailsBar(
    product: ProductWithOffer,
    onAddToCartClicked: (Int) -> Unit,
) {

    Box(contentAlignment = Alignment.BottomEnd) {

        Image(
            painterResource(id = R.drawable.offers_card_shape),
            contentDescription = null,
            Modifier.fillMaxWidth()
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            HorizontalSpacer24Dp()
            Column {
                Text(text = product.name)
                Row {
                    OldPriceText(oldPrice = product.oldPrice.toString())
                    HorizontalSpacer4Dp()
                    Text(text = "$" + product.price.toString())
                }
            }
            CartIconButtonCircularBg {
                onAddToCartClicked(product.id)
            }
        }


    }
}


@Composable
fun OldPriceText(oldPrice: String) {
    Text(text = "$$oldPrice")
}





