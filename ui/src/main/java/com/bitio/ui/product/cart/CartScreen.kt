package com.bitio.ui.product.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar

@Composable
fun CartScreen(navController: NavController) {
    CartContent(
        onBackButtonClick = navController::navigateUp
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CartContent(
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = stringResource(id = R.string.shopping_bag),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CardItem(
                modifier = Modifier.padding(vertical = it.calculateTopPadding()),
                painter = rememberAsyncImagePainter(
                    model = "https://img.freepik.com/premium-photo/funny-female-hipster-showing-tongue_251859-14529.jpg"
                ),
                onAddClick = {},
                onRemoveClick = {},
                value = ""
            )
            InfoOfCart()
        }
    }
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    value: String
) {
    ElevatedCard(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = elevatedCardElevation(0.dp),
        colors = elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter, contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(100.dp),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Black jacket",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Size : XL",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "$200.00",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = onRemoveClick,
                        modifier = Modifier
                            .border(1.dp, color = Color.White, RoundedCornerShape(50.dp))
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .border(1.dp, color = Color.White, RoundedCornerShape(50.dp))
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoOfCart(
    modifier: Modifier = Modifier,
    totalPrice: String = "$600.00"
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.sup_total),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = totalPrice,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        TextButton(
            onClick = { /*TODO*/ },
            colors = textButtonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.checkout),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
