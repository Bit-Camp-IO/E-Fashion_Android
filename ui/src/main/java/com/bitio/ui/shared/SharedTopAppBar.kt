package com.bitio.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp),
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickBackButton) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    painter = painterResource(id = R.drawable.arraw_back),
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    )
}