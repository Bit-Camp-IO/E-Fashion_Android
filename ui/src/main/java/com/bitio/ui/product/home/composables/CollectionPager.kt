package com.bitio.ui.product.home.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import com.bitio.productscomponent.domain.model.products.CollectionGroup
import com.bitio.ui.shared.AsyncDescribedImage
import java.util.regex.Pattern


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionPager(collectionGroups: List<CollectionGroup>) {
    val pagerState = rememberPagerState{collectionGroups.size}
    Box(
        contentAlignment = Alignment.BottomStart, modifier = Modifier
            .wrapContentSize()
    ) {
        HorizontalPager( state = pagerState, pageSpacing = 16.dp) {
            CollectionCard(collectionGroups[it])
        }
        PagerIndicator(size = collectionGroups.size, currentIndex = pagerState.currentPage)

    }
}


@Composable
fun CollectionCard(collectionGroup: CollectionGroup) {
    Box(
        modifier = Modifier
            .height(320.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncDescribedImage(
            collectionGroup.image,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp)),
            alpha = 0.75f
        )
        Column(
            Modifier
                .padding(start = 24.dp, bottom = 64.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {
            UnderLinedFirstWordText(text = collectionGroup.name)
            Spacer(Modifier.height(24.dp))
            Text(
                collectionGroup.getSalePercentage(),
                style = MaterialTheme.typography.displayLarge,
            )

            Text(
                text = "For Selected collection",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 64.dp, end = 24.dp)
                .size(100.dp, 42.dp)
                .clip(myShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Shop Now",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )

        }
    }
}

@Composable
fun UnderLinedFirstWordText(text: String) {
    val firstWordEndIndex = text.indexOfFirst { it == ' ' }
    val style = MaterialTheme.typography.titleLarge.copy(color = Color.White)

    Row {
        Column {
            var measuredWidth by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current
            Text(
                text = text.slice(0..firstWordEndIndex),
                style = style,
                modifier = Modifier.onGloballyPositioned {
                    measuredWidth = density.run { it.size.width.toDp() }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                thickness = 4.dp,
                modifier = Modifier.width(measuredWidth),
                color = Color.White

            )

        }
        Text(text = text.slice(firstWordEndIndex + 1..text.lastIndex), style = style)
    }

}

@Composable
fun PagerIndicator(size: Int, currentIndex: Int) {
    Row(modifier = Modifier.padding(bottom = 24.dp)) {
        Spacer(modifier = Modifier.width(24.dp))
        for (i in 0 until size) {
            val color = if (i == currentIndex) Color.White else Color.White.copy(alpha = .5f)
            Divider(thickness = 4.dp, modifier = Modifier.width(32.dp), color = color)
            Spacer(modifier = Modifier.width(4.dp))
        }

    }

}

fun CollectionGroup.getSalePercentage(): String {
    return (saleRatio * 100).toInt().toString() + "%OFF"
}


val myShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData =
            "M0 5.47826C0 2.4527 2.4527 0 5.47826 0H63.4783C83.6487 0 100 16.3513 100 36.5217V36.5217C100 39.5473 97.5473 42 94.5217 42H36.5217C16.3513 42 0 25.6487 0 5.47826V5.47826Z"

        val scaleX = size.width / 100F
        val scaleY = size.height / 42F
        return Outline.Generic(
            PathParser.createPathFromPathData(resize(pathData, scaleX, scaleY)).asComposePath()
        )
    }

    private fun resize(pathData: String, scaleX: Float, scaleY: Float): String {
        val matcher = Pattern.compile("[0-9]+[.]?([0-9]+)?")
            .matcher(pathData) // match the numbers in the path
        val stringBuffer = StringBuffer()
        var count = 0
        while (matcher.find()) {
            val number = matcher.group().toFloat()
            matcher.appendReplacement(
                stringBuffer,
                (if (count % 2 == 0) number * scaleX else number * scaleY).toString()
            ) // replace numbers with scaled numbers
            ++count
        }
        return stringBuffer.toString() // return the scaled path
    }
}
