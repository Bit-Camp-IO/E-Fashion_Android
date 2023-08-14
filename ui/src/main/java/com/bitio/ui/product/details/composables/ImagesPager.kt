package com.bitio.ui.product.details.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bitio.ui.shared.AsyncDescribedImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesPager(modifier: Modifier = Modifier, images: List<String>) {
    HorizontalPager(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        pageCount = images.size,
        pageSize = PageSize.Fill
    ) {
        AsyncDescribedImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = images[it],
            contentScale = ContentScale.Crop
        )


    }

}