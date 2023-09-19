package com.bitio.ui.authentication.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun AuthButtonForm(
    modifier: Modifier = Modifier,
    title: String,
    onClickButton: () -> Unit,
    isEnabled: Boolean,
    isSubmit:Boolean
) {
    Button(
        onClick = onClickButton,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled
    ) {
        if (isSubmit) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
            )
        }
    }
}