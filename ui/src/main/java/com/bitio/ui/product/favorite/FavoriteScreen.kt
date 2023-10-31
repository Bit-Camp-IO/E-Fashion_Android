package com.bitio.ui.product.favorite


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bitio.productscomponent.domain.model.favorites.Favorite
import com.bitio.productscomponent.domain.model.products.ProductDetails
import com.bitio.ui.R
import com.bitio.ui.product.details.navigateToProductDetailsScreen
import com.bitio.ui.shared.SharedTopAppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoriteScreen(navController: NavController) {
    val viewModel = getViewModel<FavoriteViewModel>()
    val favoriteUiState by viewModel.favoriteUIState
    val productUiState by viewModel.productUIState
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    FavoriteContent(
        favoriteUiState,
        isSheetOpen = isSheetOpen,
        onBackButtonClick = navController::navigateUp,
        onProductClick = navController::navigateToProductDetailsScreen,
        onOpenBottomSheetClick = {
            isSheetOpen = it
        },
        onAddToCartClick = {},
        onCartButtonClick = {
            viewModel.getProductDetails(it)
            isSheetOpen = true
        },
        productUiState = productUiState.product,
        onIncreaseQuantityClick = {},
        onDecreaseQuantityClick = {}
    )
}

@Composable
private fun FavoriteContent(
    favoriteUiState: FavoriteUiState,
    isSheetOpen: Boolean,
    productUiState: ProductDetails,
    onBackButtonClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onOpenBottomSheetClick: (Boolean) -> Unit,
    onAddToCartClick: (String) -> Unit,
    onCartButtonClick: (String) -> Unit,
    onIncreaseQuantityClick: (Int) -> Unit,
    onDecreaseQuantityClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            SharedTopAppBar(
                title = stringResource(id = R.string.your_favorites),
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { paddingValue ->
        LazyVerticalGrid(
            modifier = Modifier.padding(top = paddingValue.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = favoriteUiState.products.size,
                contentType = { FavoriteUiState::class.java },
                key = { it }) {
                FavoriteItem(
                    favorite = favoriteUiState.products[it],
                    onProductClick = onProductClick,
                    onCartButtonClick = onCartButtonClick
                )
            }
        }
        FavoriteBottomSheet(
            isSheetOpen = isSheetOpen,
            onOpenBottomSheetClick = onOpenBottomSheetClick,
            onAddToCartClick = onAddToCartClick,
            productUiState = productUiState,
            onDecreaseQuantityClick = onDecreaseQuantityClick,
            onIncreaseQuantityClick = onIncreaseQuantityClick
        )
    }
}

@Composable
private fun FavoriteItem(
    favorite: Favorite,
    onProductClick: (String) -> Unit,
    onCartButtonClick: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onProductClick(favorite.id)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-24).dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = favorite.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillBounds
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.full_heart),
                        contentDescription = null
                    )
                }
            }
            IconButton(
                onClick = { onCartButtonClick(favorite.id) },
                colors = iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .border(
                        4.dp,
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(50.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bag),
                    contentDescription = null,
                )
            }
        }
        Text(
            text = favorite.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "$${favorite.price}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
