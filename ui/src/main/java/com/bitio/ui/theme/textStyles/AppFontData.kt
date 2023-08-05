package com.bitio.ui.theme.textStyles

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.bitio.ui.R


object AppFontData {
    private val provider by lazy {
        GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )
    }
    private val fontName = GoogleFont("Montserrat")
    val fontFamily = FontFamily(Font(fontName, provider))
}