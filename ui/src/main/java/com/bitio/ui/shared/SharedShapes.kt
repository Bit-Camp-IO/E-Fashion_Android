package com.bitio.ui.shared

import com.bitio.utils.makeShapeFromSvgPath


val shapeOfProfile = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_PROFILE)
val shapeOfAuthForm = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_AUTHENTICATION_FORM)
val shapeOfImageProfile = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_IMAGE_PROFILE)
val shapeOfImageAuth = makeShapeFromSvgPath(SharedShapes.SHAPE_OF_IMAGE_AUTH)

private object SharedShapes{
    const val SHAPE_OF_PROFILE = "M0 50.5849C0 13.737 38.5025 -10.4526 71.6995 5.53903L331.699 130.786C349.001 139.12 360 156.628 360 175.832V556H0L0 50.5849Z"
    const val SHAPE_OF_AUTHENTICATION_FORM = SHAPE_OF_PROFILE
    const val SHAPE_OF_IMAGE_PROFILE = "M0 0H360V377.806C360 397.251 341.785 411.559 322.893 406.952L22.8933 333.802C9.45587 330.526 0 318.487 0 304.656V0Z"
    const val SHAPE_OF_IMAGE_AUTH = SHAPE_OF_IMAGE_PROFILE
}