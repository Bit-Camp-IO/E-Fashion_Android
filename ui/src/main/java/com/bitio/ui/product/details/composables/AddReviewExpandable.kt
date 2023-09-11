package com.bitio.ui.product.details.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bitio.ui.shared.CustomButtonForm
import com.bitio.ui.shared.VerticalSpacer8Dp

@Composable
fun AddReviewExpandable(onAddReview: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var review by remember { mutableStateOf("") }

    Column {

        Text(
            text = if (isExpanded) "close" else "write a review",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { isExpanded = !isExpanded })
        VerticalSpacer8Dp()
        if (isExpanded)
            Column {
                TextField(
                    value = review,
                    enabled = true,
                    onValueChange = {
                        if (review.length < 500 || it.length < review.length) review = it
                        if (review.length > 500)
                            review = review.take(500)

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                VerticalSpacer8Dp()
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = "${review.length}/500")
                }
                VerticalSpacer8Dp()
                CustomButtonForm(title = "Post Review") { onAddReview(review) }

            }

    }

}
