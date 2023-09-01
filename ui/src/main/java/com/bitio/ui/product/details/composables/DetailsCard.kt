package com.bitio.ui.product.details.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.shared.VerticalSpacer8Dp

@Composable
fun DetailsCard(productDetails: ProductDetails) {
    Box(Modifier.clickable(enabled = false) { }) {
        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.details_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
        )
        Column(Modifier.padding(horizontal = 24.dp)) {
            VerticalSpacer64Dp()
            Text(
                text = productDetails.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            VerticalSpacer8Dp()
            Text(
                text = "$" + productDetails.price.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            VerticalSpacer8Dp()
            Text(
                modifier = Modifier.width(200.dp),
                text = productDetails.description,
                style = MaterialTheme.typography.bodySmall
            )
            VerticalSpacer16Dp()


            ColorRow(colors = productDetails.colors)


            VerticalSpacer16Dp()
            CustomButtonForm(title = "Add To Cart") {}
            VerticalSpacer64Dp()
            PostRatingAndReview(onAddRating = {}, onAddReview = {})
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()

        }
    }

}


@Composable
fun PostRatingAndReview(onAddRating: (Float) -> Unit, onAddReview: (String) -> Unit) {
    DynamicRatingBar()

}

//todo behavior shall be tested with with rtl settings
@Composable
fun DynamicRatingBar() {

    var currentPointerOffset by remember { mutableStateOf(-1f) }
    Log.d("TAG1", currentPointerOffset.toString())
    var rating = 0f


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    currentPointerOffset = change.position.x
                }
            }

    ) {
        for (i in 1..5) {
            var startPosition by remember { mutableStateOf(0f) }
            rating = if (currentPointerOffset >= startPosition) i.toFloat()
            else minOf(i.toFloat(), rating)
            Box(modifier = Modifier
                .background(getColor(currentPointerOffset, startPosition))
                .onGloballyPositioned { startPosition = it.positionInParent().x }
            ) {
                WholeStar()
            }
        }
    }


}

@Composable
fun WholeStar() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.half_star_left),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.half_star_right),
            contentDescription = null,
            modifier = Modifier
                .width(64.dp)
                .aspectRatio(.5f),
            contentScale = ContentScale.FillBounds
        )
    }
}

fun getColor(positionInParent: Float, pointerDeltaX: Float): Color {
    return if (pointerDeltaX > positionInParent) Color.Red else Color.Blue
}




