package com.bitio.ui.product.cart

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.productscomponent.domain.entities.cart.CartItem
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar
import com.bitio.ui.shared.VerticalSpacer16Dp
import com.bitio.ui.shared.VerticalSpacer32Dp
import com.bitio.ui.shared.VerticalSpacer40Dp
import com.bitio.utils.TAG_APP
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
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

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    cart: CartItem,
    onIncreaseQuantityClick: (String, Int) -> Unit,
    onDecreaseQuantityClick: (String, Int) -> Unit,
    onDeleteItemSwiped: (String) -> Unit,
) {

    var quantity by remember {
        mutableIntStateOf(cart.quantity)
    }

    var isSwipedItem by remember {
        mutableStateOf(false)
    }

    if (isSwipedItem) {
        ConfirmDeleteProductOfCart(onDismiss = {
            isSwipedItem = false
        },
            onConfirm = {
                isSwipedItem = false
                onDeleteItemSwiped(cart.productId)
            })
    }

    val delete = SwipeAction(
        icon = painterResource(id = R.drawable.trash),
        background = Color.Red,
        onSwipe = {
            isSwipedItem = true
        }
    )
    SwipeableActionsBox(
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surface,
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = cart.imageUrl
                    ), contentDescription = null,
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
                        text = cart.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = cart.size,
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
                            text = cart.price.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        IconButton(
                            enabled = quantity >= 2,
                            onClick = {
                                quantity--
                                onDecreaseQuantityClick(cart.productId, quantity)
                            },
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
                            text = cart.quantity.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        IconButton(
                            onClick = {
                                quantity++
                                onIncreaseQuantityClick(cart.productId, quantity)
                            },
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
}


@Composable
private fun ConfirmDeleteProductOfCart(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Text(
                text = stringResource(id = R.string.confirm_delete_cart_item),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}


@Composable
private fun InfoOfCart(
    modifier: Modifier = Modifier,
    totalPrice: String,
    onCheckoutClick: () -> Unit
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
            onClick = onCheckoutClick,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentBottomSheet(
    isSheetOpen: Boolean,
    onOpenBottomSheetClick: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirmPaymentClick: () -> Unit,
    cart: CartItemUiState
) {
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onOpenBottomSheetClick(false)
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.checkout),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
                IconButton(onClick = {
                    onOpenBottomSheetClick(false)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.black_wallet),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.credit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.paypal),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.visa),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_payment_method),
                value = value,
                onValueChange = onValueChange
            )
            VerticalSpacer16Dp()
            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_delivery_location),
                value = value,
                onValueChange = onValueChange
            )
            VerticalSpacer40Dp()
            TextFieldOfPayment(
                placeholder = stringResource(id = R.string.label_apply_promo_code),
                value = value,
                onValueChange = onValueChange,
                imeAction = ImeAction.Done
            )
            VerticalSpacer40Dp()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
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
                        text = cart.subtotal.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.shipping),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$30.00",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Divider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.total_price),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = cart.total.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                TextButton(
                    onClick = onConfirmPaymentClick,
                    colors = textButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm_payment),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                VerticalSpacer32Dp()
            }
        }
    }
}

@Composable
private fun TextFieldOfPayment(
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
    )
}
