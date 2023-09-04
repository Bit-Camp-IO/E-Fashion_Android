package com.bitio.utils

import java.util.regex.Pattern

fun resizeShape(pathData: String, scaleX: Float, scaleY: Float): String {
    val matcher = Pattern.compile("[0-9]+[.]?([0-9]+)?").matcher(pathData)
    val stringBuffer = StringBuffer()
    var count = 0
    while (matcher.find()) {
        val number = matcher.group().toFloat()
        matcher.appendReplacement(
            stringBuffer,
            (if (count % 2 == 0) number * scaleX else number * scaleY).toString()
        )
        ++count
    }
    return stringBuffer.toString()
}

const val APP_TAG = "Debugging app"