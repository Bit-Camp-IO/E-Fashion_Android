package com.bitio.ui.product.cart

import android.annotation.SuppressLint
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
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.productscomponent.domain.entities.cart.CartItem
import com.bitio.ui.R
import com.bitio.ui.shared.SharedTopAppBar
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

    CartContent(
        onBackButtonClick = navController::navigateUp,
        onRemoveClick = {},
        onAddClick = {},
        onDeleteItemClick = viewModel::deleteCart,
        state = state
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CartContent(
    onBackButtonClick: () -> Unit,
    onAddClick: () -> Unit,
    onDeleteItemClick: (String) -> Unit,
    onRemoveClick: () -> Unit,
    state: CartItemUiState,
) {
    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = stringResource(id = R.string.shopping_bag),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = paddingValue.calculateTopPadding()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn() {
                items(
                    count = state.items.size,
                    contentType = { CartUiState::class.java },
                    key = { it }) {
                    CardItem(
                        onAddClick = onAddClick,
                        onRemoveClick = onRemoveClick,
                        cart = state.items[it]
                    )
                }
            }
            InfoOfCart(totalPrice = "$${state.subTotal}")
        }
    }
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    cart: CartItem,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDeleteItemClick: (String) -> Unit = {},
) {
    val delete = SwipeAction(
        icon = painterResource(id = R.drawable.trash),
        background = Color.Red,
        onSwipe = {
            onDeleteItemClick("")
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
                        model = "https://img.freepik.com/premium-photo/funny-female-hipster-showing-tongue_251859-14529.jpg"
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
                        text = "Black jacket",
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
                            text = cart.quantity.toString(),
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
}

@Composable
private fun InfoOfCart(
    modifier: Modifier = Modifier,
    totalPrice: String,
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
