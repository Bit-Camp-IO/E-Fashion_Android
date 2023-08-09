package com.bitio.ui.profile.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).titleMedium
) {
    Text(text = text, style = style, modifier = modifier)
}
