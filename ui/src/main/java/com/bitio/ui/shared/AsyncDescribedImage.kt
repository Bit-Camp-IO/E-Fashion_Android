package com.bitio.ui.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AsyncDescribedImage(
    imageLink: String,
    modifier: Modifier = Modifier,
    alpha: Float=1f,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = imageLink,
        contentDescription = "$imageLink Image",
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha
    )

}