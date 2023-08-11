package com.bitio.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

val profileShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val pathData =
            "M0 50.5849C0 13.737 38.5025 -10.4526 71.6995 5.53903L331.699 130.786C349.001 139.12 360 156.628 360 175.832V526C360 542.569 346.569 556 330 556H30C13.4315 556 0 542.569 0 526L0 50.5849Z"

        val scaleX = size.width / 360F
        val scaleY = size.height / 533.3F
        return Outline.Generic(
            PathParser.createPathFromPathData(resizeShape(pathData, scaleX, scaleY)).asComposePath()
        )
    }
}