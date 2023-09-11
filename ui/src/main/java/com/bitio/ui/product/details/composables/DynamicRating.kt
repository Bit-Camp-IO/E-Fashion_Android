package com.bitio.ui.product.details.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.theme.SelectedStarColor
import com.bitio.ui.theme.UnselectedStarColor
import kotlinx.coroutines.delay

//todo behavior shall be tested with with rtl settings
@Composable
fun DynamicRatingBar(onRatingChanged: (Float) -> Unit) {

    var currentPointerOffset by remember { mutableFloatStateOf(-1f) }
    var positionRelativeToWindow by remember { mutableFloatStateOf(0f) }
    val ratingList = remember { MutableList(10) { 0 } }



    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { positionRelativeToWindow = it.positionInWindow().x }
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    currentPointerOffset = positionRelativeToWindow + it.x
                    /*delay due to the fact this is an async function
                     that will be immediately fires and
                      calculate the list output before it updates*/
                    delay(500)
                    onRatingChanged(getTotalRating(ratingList))

                })
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = { onRatingChanged(getTotalRating(ratingList)) }
                ) { change, _ ->
                    change.consume()
                    currentPointerOffset = positionRelativeToWindow + change.position.x
                }

            }


    ) {
        for (i in 1..5) {
            var startPosition by remember { mutableFloatStateOf(0f) }


            Box(modifier = Modifier
                .onGloballyPositioned { startPosition = it.positionInParent().x }
            ) {
                WholeStar(currentPointerOffset, ratingList, i)
            }
        }
    }
}

@Composable
fun WholeStar(currentPointerXOffset: Float, ratingList: MutableList<Int>, index: Int) {
    var leftStarXPosition by remember { mutableFloatStateOf(0f) }
    var rightStarXPosition by remember { mutableFloatStateOf(0f) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ratingList[(index - 1) * 2] = if (leftStarXPosition < currentPointerXOffset) 1 else 0
        ratingList[(index - 1) * 2 + 1] = if (rightStarXPosition < currentPointerXOffset) 1 else 0
        Log.d("TAG1", index.toString())
        Image(
            painter = painterResource(id = R.drawable.half_star_left),
            contentDescription = null,
            modifier = Modifier
                .width(24.dp)
                .aspectRatio(.5f)
                .onGloballyPositioned { leftStarXPosition = it.positionInWindow().x },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(getColor(leftStarXPosition, currentPointerXOffset))
        )
        Image(
            painter = painterResource(id = R.drawable.half_star_right),
            contentDescription = null,
            modifier = Modifier
                .width(24.dp)
                .aspectRatio(.5f)
                .onGloballyPositioned { rightStarXPosition = it.positionInWindow().x },
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(getColor(rightStarXPosition, currentPointerXOffset))
        )
    }
}

fun getColor(positionInParent: Float, pointerDeltaX: Float): Color {
    return if (pointerDeltaX > positionInParent) SelectedStarColor else UnselectedStarColor
}

fun getTotalRating(ratingList: List<Int>): Float {
    return ratingList.reduce { acc, i -> acc + i }.toFloat() / 2f
}