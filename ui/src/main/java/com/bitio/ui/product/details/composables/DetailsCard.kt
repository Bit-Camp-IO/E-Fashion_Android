package com.bitio.ui.product.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.utils.makeShapeFromSvgPath

@Composable
fun DetailsCard(productDetails: ProductDetails) {
    Box(Modifier.clickable(enabled = false) {  }) {
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


            for (selectable in productDetails.selectableProperties) {
                SelectableRowStrategy(selectable = selectable)
                VerticalSpacer16Dp()
            }
            VerticalSpacer16Dp()
            CustomButtonForm(title = "Add To Cart") {


            }
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()

        }
    }

}


const val cardSvgPath =
    "M0 50.0161C0 18.5728 28.6783 -5.06615 59.5435 0.93542L319.543 51.491C343.038 56.0594 360 76.6371 360 100.572V455.358L0 451.967V50.0161Z"

fun cardShape() = makeShapeFromSvgPath(cardSvgPath)






