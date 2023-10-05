package com.bitio.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
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

const val TAG_APP = "Debugging_App"

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}