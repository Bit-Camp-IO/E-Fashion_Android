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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer24Dp
import com.bitio.ui.shared.VerticalSpacer64Dp

@Composable
fun OrderStatusScreen(
    viewModel: OrderStatusViewModel = hiltViewModel()
) {
    val state by viewModel.checkOrderString.collectAsState()
    OrderStatusContent(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun OrderStatusContent(state: OrderStatusUiState) {
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
                .padding(top = paddingValue.calculateTopPadding(), end = 24.dp, start = 24.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val ordersStatus = state.ordersStatus
            CheckOrderStatus(ordersStatus)
            ordersStatus.filter { it.isOrderStatusActive }.let { ordersStatus ->
                val order = ordersStatus.first()
                DetailsOfOrderStatus(
                    typeOrderStatus = order.typeOrderStatus,
                    description = order.description
                )
            }
            VerticalSpacer24Dp()
        }
    }
}

@Composable
private fun CheckOrderStatus(ordersStatus: List<OrderStatus>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in ordersStatus.indices) {
                CircleShape(orderStatus = ordersStatus[i])
                if (i < ordersStatus.size - 1) {
                    CustomDivider(
                        status = ordersStatus[i].isOrderStatusActive,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        TitleOfOrderStatus(ordersStatus)
    }
}

@Composable
private fun CircleShape(orderStatus: OrderStatus) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .size(24.dp)
            .border(
                width = 1.dp,
                color = if (orderStatus.isOrderStatusActive) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (orderStatus.isOrderStatusActive) {
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
private fun TitleOfOrderStatus(ordersStatus: List<OrderStatus>) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ordersStatus.forEach {
            Text(
                text = it.title,
                color = if (it.isOrderStatusActive) MaterialTheme.colorScheme.primary else Color.Gray,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@Composable
private fun DetailsOfOrderStatus(
    typeOrderStatus: TypeOrderStatus,
    description: String,
) {
    Column(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 132.dp, height = 200.dp),
            painter = painterResource(
                id = when (typeOrderStatus) {
                    TypeOrderStatus.OnProgress -> {
                        R.drawable.order_progress
                    }

                    TypeOrderStatus.OnWay -> {
                        R.drawable.order_on_way
                    }

                    TypeOrderStatus.Delivered -> {
                        R.drawable.order_delivered
                    }
                }
            ), contentDescription = null
        )
        VerticalSpacer24Dp()
        Text(
            text = description,
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}

