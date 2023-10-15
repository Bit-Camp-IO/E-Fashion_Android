package com.bitio.ui.product.cart.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bitio.productscomponent.domain.entities.cart.CartItem
import com.bitio.ui.R
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
internal fun CardItem(
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
        },
    )


    SwipeableActionsBox(
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .padding(horizontal = 24.dp),
        swipeThreshold = 100.dp
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth(), shape = RoundedCornerShape(24.dp),
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