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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.shared.VerticalSpacer24Dp
import org.koin.androidx.compose.getViewModel

@Composable
fun OrderStatusScreen(

) {
    val viewModel = getViewModel<OrderStatusViewModel>()
    val state by viewModel.checkOrderString.collectAsState()
    OrderStatusContent(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun OrderStatusContent(state: OrderStatusUiState) {

    var isThereOrderStatus by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 24.dp),
                title = {
                    Text("Order status", style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.padding(end = 16.dp),
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

            OrderStatusHeader(state.ordersStatus)
            state.ordersStatus.forEach {
                it.takeIf { it.isOrderStatusActive }?.let { order ->
                    isThereOrderStatus = true
                    OrderStatusBody(
                        painter = painterResource(id = order.imageOfOrderStatus),
                        description = order.description
                    )
                }
            }
            if (!isThereOrderStatus) {
                OrderStatusBody(
                    painter = painterResource(id = R.drawable.box_empty),
                    description = "There is no order yet"
                )
            }
            VerticalSpacer24Dp()
        }
    }
}

@Composable
private fun OrderStatusHeader(ordersStatus: List<OrderStatus>) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
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
private fun OrderStatusBody(
    painter: Painter,
    description: String,
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .width(250.dp)
                .wrapContentSize(),
            painter = painter,
            contentDescription = null,
        )

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
