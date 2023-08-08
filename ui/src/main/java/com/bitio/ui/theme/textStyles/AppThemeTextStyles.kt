package com.bitio.ui.theme.textStyles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class AppThemeTextStyles(fontColor: Color) {
    val displayLarge by lazy { TextStyle(
        fontFamily = AppFontData.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        color = Color.White
    )  }
    val titleLarge by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 24.sp,
            color = fontColor
        )
    }
    val titleMedium by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            color = fontColor
        )
    }
    val titleSmall by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            color = fontColor

        )
    }
    val bodyLarge by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            color = fontColor

        )
    }
    val bodyMedium by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            color = fontColor

        )
    }
    val bodySmall by lazy {
        TextStyle(
            fontFamily = AppFontData.fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = fontColor

        )
    }
}