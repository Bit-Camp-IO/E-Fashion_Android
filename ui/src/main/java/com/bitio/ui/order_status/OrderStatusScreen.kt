package com.bitio.ui.order_status

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitio.ui.R
import com.bitio.ui.shared.VerticalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer8Dp

@Composable
fun OrderStatusScreen(
    viewModel: OrderStatusViewModel = hiltViewModel()
) {
    val state by viewModel.checkOrderString.collectAsState()
    OrderStatusContent(
        state,
        onClickProgress = viewModel::onProgressOrder,
        onClickOnWay = viewModel::onWayOrder,
        onClickDeliver = viewModel::onClickDeliver,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun OrderStatusContent(
    state: OrderStatusUiState,
    onClickProgress: (Boolean) -> Unit,
    onClickOnWay: (Boolean) -> Unit,
    onClickDeliver: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Order status")
            },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arraw_back),
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                })
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(top = paddingValue.calculateTopPadding())
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            StatusOfOrder(state = state)
            ImageOrderStatus(state = state)
            ChangeOrderStatus(
                onClickProgress = onClickProgress,
                onClickOnWay = onClickOnWay,
                onClickDeliver = onClickDeliver,
            )
        }
    }
}

@Composable
private fun StatusOfOrder(
    state: OrderStatusUiState
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IsCheckStatus(status = state.isProgress)
            CustomDivider(
                status = state.isProgress,
                modifier = Modifier.weight(1f)
            )
            IsCheckStatus(status = state.isItOnWay)
            CustomDivider(
                status = state.isItOnWay,
                modifier = Modifier.weight(1f)
            )
            IsCheckStatus(status = state.isDelivered)
        }
        VerticalSpacer8Dp()
        TitleOfStatusOrder(state = state)
    }
}

@Composable
private fun IsCheckStatus(status: Boolean) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .size(24.dp)
            .border(
                width = 1.dp,
                color = if (status) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (status) {
            Icon(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "check",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CustomDivider(
    status: Boolean,
    modifier: Modifier = Modifier
) {
    Divider(
        color = if (status) MaterialTheme.colorScheme.primary else Color.Gray,
        modifier = modifier
    )
}

@Composable
private fun TitleOfStatusOrder(
    state: OrderStatusUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "On Progress",
            color = if (state.isProgress) MaterialTheme.colorScheme.primary else Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "On its way",
            color = if (state.isItOnWay) MaterialTheme.colorScheme.primary else Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Delivered",
            color = if (state.isDelivered) MaterialTheme.colorScheme.primary else Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ImageOrderStatus(
    state: OrderStatusUiState
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 64.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = if (state.isProgress) {
                    R.drawable.order_progress
                } else if (state.isItOnWay) {
                    R.drawable.order_on_way
                } else {
                    R.drawable.order_delivered
                }
            ),
            contentDescription = null
        )
        VerticalSpacer24Dp()
        Text(
            text = if (state.isProgress) {
                "Your order still on progress it may take hours "
            } else if (state.isItOnWay) {
                "The delivery man on his way to your location "
            } else {
                "Your order has been delivered, hope you liked it"
            },
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ChangeOrderStatus(
    onClickProgress: (Boolean) -> Unit,
    onClickOnWay: (Boolean) -> Unit,
    onClickDeliver: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = {
            onClickProgress(true)
            onClickOnWay(false)
            onClickDeliver(false)
        }) {
            Text(
                text = "On Progress",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(onClick = {
            onClickProgress(false)
            onClickOnWay(true)
            onClickDeliver(false)
        }) {
            Text(
                text = "On its way",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(onClick = {
            onClickProgress(false)
            onClickOnWay(false)
            onClickDeliver(true)
        }) {
            Text(
                text = "Delivered",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}