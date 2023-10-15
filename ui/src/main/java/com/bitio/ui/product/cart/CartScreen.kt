package com.bitio.ui.product.cart

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitio.ui.R
import com.bitio.ui.product.cart.composable.CardItem
import com.bitio.ui.product.cart.composable.InfoOfCart
import com.bitio.ui.product.cart.composable.PaymentBottomSheet
import com.bitio.ui.shared.SharedTopAppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun CartScreen(navController: NavController) {
    val viewModel = getViewModel<CartViewModel>()
    val state by viewModel.cartUiState
    val context = LocalContext.current

    if (state.message.isNotEmpty()) {
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    CartContent(
        state = state,
        isSheetOpen = isSheetOpen,
        onBackButtonClick = navController::navigateUp,
        onDecreaseQuantityClick = viewModel::editCart,
        onIncreaseQuantityClick = viewModel::editCart,
        onDeleteItemSwiped = viewModel::deleteCart,
        onCheckoutClick = {
            isSheetOpen = true
        },
        onOpenBottomSheetClick = {
            isSheetOpen = it
        },
        value = "",
        onValueChange = {},
        onConfirmPaymentClick = {},
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CartContent(
    state: CartItemUiState,
    isSheetOpen: Boolean,
    onBackButtonClick: () -> Unit,
    onIncreaseQuantityClick: (String, Int) -> Unit,
    onDecreaseQuantityClick: (String, Int) -> Unit,
    onDeleteItemSwiped: (String) -> Unit,
    onCheckoutClick: () -> Unit,
    onOpenBottomSheetClick: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirmPaymentClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = stringResource(id = R.string.shopping_bag),
                onBackButtonClick = onBackButtonClick
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                modifier = Modifier.height(screenHeight / (1.7).toFloat()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    count = state.items.size,
                    contentType = { CartUiState::class.java },
                    key = { it }) {
                    CardItem(
                        onIncreaseQuantityClick = onIncreaseQuantityClick,
                        onDecreaseQuantityClick = onDecreaseQuantityClick,
                        onDeleteItemSwiped = onDeleteItemSwiped,
                        cart = state.items[it]
                    )
                }
            }
            InfoOfCart(
                modifier = Modifier.height(200.dp),
                totalPrice = "$${state.subtotal}",
                onCheckoutClick = onCheckoutClick
            )
        }
        PaymentBottomSheet(
            isSheetOpen = isSheetOpen,
            onOpenBottomSheetClick = onOpenBottomSheetClick,
            value = value,
            onValueChange = onValueChange,
            onConfirmPaymentClick = onConfirmPaymentClick,
            cart = state
        )
    }
}