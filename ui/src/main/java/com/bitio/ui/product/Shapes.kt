package com.bitio.ui.product

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

fun firstItemLargeShape() =
    makeShapeFromSvgPath(SvgLargePaths.FIRST)

fun middleItemsLargeShape() = makeShapeFromSvgPath(SvgLargePaths.MIDDLE)

fun lastItemLargeShape() = makeShapeFromSvgPath(SvgLargePaths.LAST)

fun firstLeftShape()= makeShapeFromSvgPath(SvgSmallPaths.FIRST_LEFT)
fun firstRightShape()= makeShapeFromSvgPath(SvgSmallPaths.FIRST_RIGHT)
fun middleLeftShape()= makeShapeFromSvgPath(SvgSmallPaths.MIDDLE_LEFT)
fun middleRightShape()= makeShapeFromSvgPath(SvgSmallPaths.MIDDLE_RIGHT)


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
                size.width / pathSize.width, size.height / pathSize.height
            )
            path.asAndroidPath().transform(matrix)

            return Outline.Generic(path)
        }
    }
}


object SvgLargePaths {
    const val FIRST =
        "M0 16C0 7.16345 7.16344 0 16 0H296C304.837 0 312 7.16344 312 16V300L0 257.143V16Z"
    const val MIDDLE = "M0 0L312 40V320L0 280L0 0Z"
    const val LAST =
        "M0 16.1823C0 6.53804 8.4687 -0.914269 18.0346 0.312132L298.035 36.2096C306.019 37.2332 312 44.0297 312 52.0797V302C312 310.837 304.837 318 296 318H16C7.16344 318 0 310.837 0 302L0 16.1823Z"


}

object SvgSmallPaths {
    const val FIRST_LEFT = "M150 0H16C7.16345 0 0 7.16344 0 16V200L150 180V0Z"
    const val FIRST_RIGHT = "M0 0H134C142.837 0 150 7.16344 150 16V200L0 180V0Z"
    const val MIDDLE_LEFT = "M150 0L0 20V200L150 180V0Z"
    const val MIDDLE_RIGHT = "M0 0L150 20V200L0 180V0Z"


}

