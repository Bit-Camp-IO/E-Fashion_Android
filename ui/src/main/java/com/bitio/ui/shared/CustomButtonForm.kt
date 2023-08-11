package com.bitio.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun CustomButtonForm(
    modifier: Modifier = Modifier,
    title: String,
    onClickLoginButton: () -> Unit,
) {
    Button(
        onClick = onClickLoginButton,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = title,
            style = AppThemeTextStyles(Color.White).titleMedium,
            modifier = Modifier
        )
    }
}