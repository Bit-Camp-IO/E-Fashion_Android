package com.bitio.ui.product.favorite

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.graphics.toColorInt
import com.bitio.productscomponent.domain.model.products.ColorOfProduct
import com.bitio.productscomponent.domain.model.products.ProductDetails
import com.bitio.ui.R
import com.bitio.ui.shared.HorizontalSpacer50Dp
import com.bitio.ui.shared.HorizontalSpacer8Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoriteBottomSheet(
    productUiState: ProductDetails,
    isSheetOpen: Boolean,
    isLoading: Boolean,
    onOpenBottomSheetClick: (Boolean) -> Unit,
    onAddToCartClick: (CartItemUiState) -> Unit,
) {

    var quantity by remember {
        mutableIntStateOf(0)
    }
    var color by remember {
        mutableStateOf("")
    }
    var size by remember {
        mutableStateOf("")
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onOpenBottomSheetClick(false)
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = productUiState.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$${productUiState.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (productUiState.sizes.isNotEmpty()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.color),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    HorizontalSpacer8Dp()
                    ColorsOfProduct(colors = productUiState.colors, onColorSelected = {
                        color = it
                    })
                }
            }
            if (productUiState.colors.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.size),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    HorizontalSpacer8Dp()
                    SizesOfProduct(sizes = productUiState.sizes, onSizeSelected = {
                        size = it
                    })
                }
            }

            if (productUiState.isAvailable) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = productUiState.stock.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    HorizontalSpacer8Dp()
                    Text(
                        text = stringResource(id = R.string.pieces_available),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    HorizontalSpacer50Dp()
                    StockOFProduct(
                        stock = productUiState.stock,
                        onIncreaseQuantityClick = {
                            quantity = it
                        },
                        onDecreaseQuantityClick = {
                            quantity = it
                        }
                    )
                }
            }

            ButtonAddProductToCart(
                onAddToCartClick = {
                    onAddToCartClick(
                        CartItemUiState(
                            id = productUiState.id,
                            size = size,
                            color = color,
                            quantity = quantity,
                        )
                    )
                },
                isEnabled = quantity > 0 && color.isNotEmpty() && size.isNotEmpty(),
                isLoading = isLoading
            )
        }
    }
}

@Composable
private fun ColorsOfProduct(
    colors: List<ColorOfProduct>,
    onColorSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(count = colors.size,
            contentType = { ColorOfProduct::class.java },
            key = { it }
        ) {
            SelectColor(
                color = colors[it].hex,
                onColorSelected = {
                    onColorSelected(colors[it].hex)
                },
            )
        }
    }
}

@Composable
private fun SelectColor(
    color: String,
    onColorSelected: () -> Unit,
) {

    IconButton(
        onClick = onColorSelected,
        colors = iconButtonColors(containerColor = Color(color.toColorInt())),
        modifier = Modifier
            .size(24.dp)
    ) {
    }
}


@Composable
private fun SizesOfProduct(
    sizes: List<String>,
    onSizeSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(count = sizes.size,
            contentType = { String::class.java },
            key = { it }
        ) {
            SelectSize(
                size = sizes[it],
                onSizeSelected = onSizeSelected
            )
        }
    }
}

@Composable
private fun SelectSize(
    size: String,
    onSizeSelected: (String) -> Unit,
) {

    TextButton(
        onClick = { onSizeSelected(size) },
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
    ) {
        Text(
            text = size,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun StockOFProduct(
    stock: Int,
    onIncreaseQuantityClick: (Int) -> Unit,
    onDecreaseQuantityClick: (Int) -> Unit,
) {
    var quantity by remember {
        mutableIntStateOf(0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        IconButton(
            enabled = quantity >= 1,
            onClick = {
                quantity--
                onDecreaseQuantityClick(quantity)
            },
            modifier = Modifier
                .border(1.dp, color = Color.White, RoundedCornerShape(50.dp))
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        IconButton(
            enabled = quantity <= stock - 1,
            onClick = {
                quantity++
                onIncreaseQuantityClick(quantity)
            },
            modifier = Modifier
                .border(1.dp, color = Color.White, RoundedCornerShape(50.dp))
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ButtonAddProductToCart(
    isEnabled: Boolean,
    isLoading: Boolean,
    onAddToCartClick: () -> Unit,
) {
    TextButton(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled,
        onClick = { onAddToCartClick() },
        colors = textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary
        )
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bag),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                HorizontalSpacer8Dp()
                Text(
                    text = stringResource(id = R.string.add_to_cart),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

