package com.bitio.ui.product.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty
import com.bitio.ui.shared.HorizontalSpacer8Dp

@Composable
fun SelectableRowStrategy(selectable: SelectableProperty) {
    if (!SpecialOptions.isSpecialSelectable(selectable.id))
        SelectableRow(selectable)
    else
        when (SpecialOptions.getCorrespondingSpecialSelectable(selectable.id)) {
            SpecialOptions.COLOR -> ColorRow(selectable = selectable)
            SpecialOptions.IMAGE -> ImageRow(selectable = selectable)
        }


}


@Composable
fun SelectableRow(selectable: SelectableProperty) {
    Row {
        Text(text = selectable.name + ": ")
        HorizontalSpacer8Dp()
        LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            items(selectable.options.size) {
                Text(text = selectable.options[it].name)

            }
        }
    }

}

@Composable
fun ColorRow(selectable: SelectableProperty) {
    Row {
        Text(text = selectable.name + ": ")
        HorizontalSpacer8Dp()
        LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            items(2) {
                val color = remember { selectable.options[it].name.toColor() }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun ImageRow(selectable: SelectableProperty) {
    LazyRow() {
        items(selectable.options.size) {
            Text(text = selectable.options[it].name)
        }
    }
}

fun String.toColor(): Color {
    return Color(this.toLong(16))

}