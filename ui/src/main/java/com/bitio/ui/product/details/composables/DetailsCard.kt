package com.bitio.ui.product.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.bitio.productscomponent.domain.model.products.ProductDetails
import com.bitio.ui.R
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer40Dp
import com.bitio.ui.shared.VerticalSpacer8Dp

@Composable
fun DetailsCard(productDetails: ProductDetails) {

    Box {

        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clickable(enabled = false) { },
            painter = painterResource(id = R.drawable.details_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {

            Column(Modifier.padding(horizontal = 24.dp)) {

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
                CustomButtonForm(title = "Add To Cart", onClickButton = {})
                VerticalSpacer40Dp()
                PostRatingAndReview(onAddRating = {}, onAddReview = {})
                VerticalSpacer40Dp()
                VerticalSpacer40Dp()
                VerticalSpacer40Dp()
                VerticalSpacer40Dp()

            }
        }

    }

}


@Composable
fun PostRatingAndReview(onAddRating: (Float) -> Unit, onAddReview: (String) -> Unit) {
    Text(
        text = "Rate this product",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = "Tell others what you think",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    DynamicRatingBar(onAddRating)
    VerticalSpacer16Dp()
    AddReviewExpandable(onAddReview = onAddReview)

}

@Composable
fun RatingAndReviews() {
    Column {
        Column {
            Text(text = "Rating and Reviews")
            Text(text = "See what others think about this product")
        }
        Column {
            Text(text = "4.9")

        }
    }
}

interface ReviewAndRatingDetails {
    //val totalRating: Float
    val ratingsCount: Int
    val totalReviewsCount: Int
    val ratingList: List<Float>
    val topReviewsList: List<Review>
}

interface Review {
    val id: String
    val reviewWriterName: String
    val userRating: Float
    val reviewDate: String
    val reviewBody: String
}
