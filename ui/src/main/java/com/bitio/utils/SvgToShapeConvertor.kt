package com.bitio.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

fun makeShapeFromSvgPath(svgPath: String): Shape {
    val path = PathParser.createPathFromPathData(svgPath).asComposePath()
    val pathSize = path.getBounds().size
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val matrix = android.graphics.Matrix()
            matrix.postScale(
                size.width / pathSize.width,
                size.height / pathSize.height
            )
            path.asAndroidPath().transform(matrix)

            return Outline.Generic(path)
        }
    }
}