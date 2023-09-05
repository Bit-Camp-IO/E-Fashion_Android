package com.bitio.ui.shared

import com.bitio.utils.makeShapeFromSvgPath


val shapeOfProfile = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_PROFILE)
val shapeOfAuthForm = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_AUTHENTICATION_FORM)

private object SharedShapes{
    const val SHAPE_OF_PROFILE = "M0 50.5849C0 13.737 38.5025 -10.4526 71.6995 5.53903L331.699 130.786C349.001 139.12 360 156.628 360 175.832V526C360 542.569 346.569 556 330 556H30C13.4315 556 0 542.569 0 526L0 50.5849Z"
    const val SHAPE_OF_AUTHENTICATION_FORM = SHAPE_OF_PROFILE
}