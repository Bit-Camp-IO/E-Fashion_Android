package com.bitio.ui.product.productsList

import com.bitio.utils.makeShapeFromSvgPath

val firstItemLargeShape = makeShapeFromSvgPath(SvgLargePaths.FIRST)

val middleItemsLargeShape = makeShapeFromSvgPath(SvgLargePaths.MIDDLE)

val lastItemLargeShape = makeShapeFromSvgPath(SvgLargePaths.LAST)


val firstLeftShape = makeShapeFromSvgPath(SvgSmallPaths.FIRST_LEFT)
val firstRightShape = makeShapeFromSvgPath(SvgSmallPaths.FIRST_RIGHT)
val middleLeftShape = makeShapeFromSvgPath(SvgSmallPaths.MIDDLE_LEFT)
val middleRightShape = makeShapeFromSvgPath(SvgSmallPaths.MIDDLE_RIGHT)
val leftCurve = makeShapeFromSvgPath(SvgSmallPaths.LEFT_CURVE)
val rightCurve = makeShapeFromSvgPath(SvgSmallPaths.Right_CURVE)


object SvgLargePaths {
    const val FIRST =
        "M0 16C0 7.16344 7.16344 0 16 0H304C312.837 0 320 7.16344 320 16V320L0 280V16Z"

    //  "M0 16C0 7.16345 7.16344 0 16 0H296C304.837 0 312 7.16344 312 16V300L0 257.143V16Z"
    const val MIDDLE = "M0 0L320 40V320L0 280L0 0Z" //"M0 0L312 40V320L0 280L0 0Z"

    const val LAST =
        "M0 16.1823C0 6.53804 8.4687 -0.914269 18.0346 0.312132L298.035 36.2096C306.019 37.2332 312 44.0297 312 52.0797V302C312 310.837 304.837 318 296 318H16C7.16344 318 0 310.837 0 302L0 16.1823Z"


}

object SvgSmallPaths {
    const val FIRST_LEFT = "M150 0H16C7.16345 0 0 7.16344 0 16V200L150 180V0Z"
    const val FIRST_RIGHT = "M0 0H134C142.837 0 150 7.16344 150 16V200L0 180V0Z"
    const val MIDDLE_LEFT = "M150 0L0 20V200L150 180V0Z"
    const val MIDDLE_RIGHT = "M0 0L150 20V200L0 180V0Z"
    const val LEFT_CURVE = "M0 73 H150 V13 C132.8 9.6 45.7 -6.8 0 3.32V73Z"
    const val Right_CURVE = "M150 73 H0 V13 C17.2 9.5 104.3 -6.8 150 3.2 V72.9Z"


}



