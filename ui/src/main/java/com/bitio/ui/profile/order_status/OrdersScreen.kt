package com.bitio.ui.profile.order_status

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import com.bitio.productscomponent.domain.entities.order.Order
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar
import org.koin.androidx.compose.getViewModel


@Composable
fun OrdersScreen(navController: NavController) {
    val viewModel = getViewModel<OrderStatusViewModel>()
    val state by viewModel.orderUiState
    OrderContent(state, onBackButtonClick = navController::navigateUp)
}

@Composable
private fun OrderContent(
    state: OrderUiState,
    onBackButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = stringResource(id = R.string.order_status),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier.padding(top = paddingValue.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = state.orders.size,
                contentType = { OrderUiState::class.java },
                key = { it }) {
                OrderItem(order = state.orders[it])
            }
        }
    }
}

@Composable
private fun OrderItem(
    modifier: Modifier = Modifier,
    order: Order,
) {

    val statusOrder = when (order.status) {
        1 -> stringResource(id = R.string.on_progress)
        2 -> stringResource(id = R.string.on_it_is_way)
        else -> stringResource(id = R.string.delivered)
    }

    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var orderId by remember { mutableStateOf("") }

    ElevatedCard(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.delivery), contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(60.dp),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "#${order.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString((order.id)))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.solar_copy),
                            contentDescription = stringResource(
                                id = R.string.copy_id_of_product
                            )
                        )
                    }
                }
                Text(
                    text = "$${order.price}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = statusOrder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

