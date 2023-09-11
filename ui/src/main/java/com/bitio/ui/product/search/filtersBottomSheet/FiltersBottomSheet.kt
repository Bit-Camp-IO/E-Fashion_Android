@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.bitio.ui.product.search.filtersBottomSheet

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.product.models.UiCategory
import com.bitio.ui.product.models.UiSize
import com.bitio.ui.shared.HorizontalSpacer8Dp
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer2Dp
import com.bitio.ui.shared.VerticalSpacer4Dp
import com.bitio.ui.shared.VerticalSpacer64Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBottomSheet(
    isExpanded: MutableState<Boolean>,
    categories: MutableState<List<UiCategory>>,
    sizes: List<UiSize>,
    priceRange: MutableState<ClosedFloatingPointRange<Float>>,
    onClosed: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = isExpanded.value) {
        delay(500)
        if (isExpanded.value) sheetState.expand() else sheetState.hide()
    }


    ModalBottomSheet(onDismissRequest = { isExpanded.value = false }, sheetState = sheetState) {

        Column(
            Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            AvailableCategoriesShips(categories = categories)
            VerticalSpacer16Dp()
            AvailableSizes(sizes = sizes)
            PriceRange(currentRangeState = priceRange)
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
            VerticalSpacer64Dp()
        }
    }
}


@Composable
fun AvailableCategoriesShips(categories: MutableState<List<UiCategory>>) {
    Column {
        Text(text = "Category")
        VerticalSpacer4Dp()
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        VerticalSpacer8Dp()
        FlowRow {
            for (category in categories.value)
                CategoryChip(category)
        }
    }
}


@Composable
fun CategoryChip(category: UiCategory) {
    FilterChip(
        modifier = Modifier.padding(horizontal = 8.dp),
        selected = category.selectedState.value,
        onClick = { category.selectedState.value = !category.selectedState.value },
        label = { Text(text = category.name) },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onSurface,
            selectedBorderColor = MaterialTheme.colorScheme.onSurface,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        )
    )
}

@Composable
fun AvailableSizes(sizes: List<UiSize>) {
    AvailableItemsChipsFlow(title = "Size", items = sizes) { SizeChip(size = it) }
}

@Composable
fun SizeChip(size: UiSize) {
    FilterChip(
        modifier = Modifier.padding(horizontal = 8.dp),
        selected = size.isSelected.value,
        onClick = { size.isSelected.value = !size.isSelected.value },
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(1.dp, Color.Gray), CircleShape)
                ) {
                    if (size.isSelected.value)
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(1.dp)
                                .background(MaterialTheme.colorScheme.primary),
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = null,
                            tint = Color.White
                        )
                }
                HorizontalSpacer8Dp()
                Text(text = size.name)
            }
        },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color.Unspecified,
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onSurface,
            selectedBorderColor = MaterialTheme.colorScheme.onSurface,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        )


    )


    Box(
        modifier = Modifier
            .wrapContentSize()

            .clickable { }
    ) {

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Any> AvailableItemsChipsFlow(
    title: String,
    items: List<T>,
    chip: @Composable (T) -> Unit
) {
    Column {
        Text(text = title)
        VerticalSpacer4Dp()
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        VerticalSpacer8Dp()
        FlowRow {
            for (item in items)
                chip(item)
        }
    }
}

@Composable
fun PriceRange(currentRangeState: MutableState<ClosedFloatingPointRange<Float>>) {
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }
    var composableWidth by remember { mutableIntStateOf(0) }
    var composableOffset by remember { mutableFloatStateOf(0f) }

    Text(text = "Price Ranger")
    VerticalSpacer4Dp()
    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
    RangeSlider(modifier = Modifier.onGloballyPositioned {
        composableWidth = it.size.width
        composableOffset = it.positionInWindow().x

    },
        value = currentRangeState.value,
        onValueChange = { currentRangeState.value = it },
        valueRange = 0f..1000f,
        steps = 0,
        colors = SliderDefaults.colors(),
        startInteractionSource = startInteractionSource,
        endInteractionSource = endInteractionSource,
        startThumb ={
            val showLabel = remember { mutableStateOf(false) }
            LaunchedEffect(key1 = startInteractionSource) {
                endInteractionSource.interactions.collect {
                    when (it) {
                        is DragInteraction.Start -> showLabel.value = true

                        else -> showLabel.value = false

                    }
                    Log.d("bbbb", it.toString())
                }


            }
            UserThumb(
                isThumbPressed = showLabel.value,
                currentValue = currentRangeState.value.start,
                interactionSource = startInteractionSource
            )
        },
        endThumb = {
            val showLabel = remember { mutableStateOf(false) }
            LaunchedEffect(key1 = endInteractionSource) {
                endInteractionSource.interactions.collect {
                    when (it) {
                        is DragInteraction.Start -> showLabel.value = true

                        else -> showLabel.value = false

                    }
                    Log.d("bbbb", it.toString())
                }


            }
            UserThumb(
                isThumbPressed = showLabel.value,
                currentValue = currentRangeState.value.endInclusive,
                interactionSource = endInteractionSource
            )
        }
    )
}

@Composable
fun UserThumb(
    isThumbPressed: Boolean,
    currentValue: Float,
    interactionSource: MutableInteractionSource
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isThumbPressed) {
            Box(
                modifier = Modifier
                    .requiredHeight(16.dp)
                    .requiredWidth(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentValue.roundToInt().toString(),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else VerticalSpacer16Dp()
        VerticalSpacer2Dp()
        SliderDefaults.Thumb(interactionSource = interactionSource)
        VerticalSpacer2Dp()
        VerticalSpacer8Dp()
        VerticalSpacer8Dp()
    }


}
