package com.bitio.ui.product.productsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bitio.ui.R
import com.bitio.ui.product.productsList.largeCards.ProductParallelogramColumn
import com.bitio.ui.product.productsList.smallCards.ProductParallelogramGrid
import com.bitio.ui.shared.HorizontalSpacer32Dp
import com.bitio.ui.shared.HorizontalSpacer8Dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(navController: NavController) {
    val isGridDisplayMode = remember { mutableStateOf(false) }
    val viewModel = koinViewModel<ProductListViewModel>()
    val data = viewModel.flow.collectAsLazyPagingItems()

    Column {
        AppTopNavBar(title = "Zara", navController) {
            ListOrGridViewViewSelector(isGridDisplayMode)
        }
        if (isGridDisplayMode.value)
            ProductParallelogramGrid(lazyProducts =data) else ProductParallelogramColumn(data)
    }


}

@Composable
fun AppTopNavBar(
    title: String,
    navController: NavController,
    suffix: @Composable () -> Unit = { HorizontalSpacer8Dp() }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arraw_back),
                    contentDescription = ""
                )
            }
            HorizontalSpacer8Dp()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        suffix()
    }
}

@Composable
fun ListOrGridViewViewSelector(isGridDisplayMode: MutableState<Boolean>) {

    Row {
        Icon(
            painter = painterResource(id = R.drawable.one_item),
            contentDescription = null,
            tint = if (!isGridDisplayMode.value) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable {

                isGridDisplayMode.value = !isGridDisplayMode.value
            }
        )
        HorizontalSpacer32Dp()
        Icon(
            painter = painterResource(id = R.drawable.multi_item), contentDescription = null,
            tint = if (isGridDisplayMode.value) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface, modifier = Modifier.clickable {

                isGridDisplayMode.value = !isGridDisplayMode.value
            }
        )
    }


}
