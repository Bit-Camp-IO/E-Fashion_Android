package com.bitio.ui.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AsyncDescribedImage(
    imageLink: String,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    AsyncImage(
        model = imageLink,
        contentDescription = "product Image",
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha
    )

}