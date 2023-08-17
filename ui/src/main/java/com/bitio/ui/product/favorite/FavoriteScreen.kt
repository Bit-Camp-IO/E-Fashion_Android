@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitio.ui.product.favorite

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitio.ui.R
import com.bitio.ui.product.home.productWithOffer
import com.bitio.ui.product.productsList.largeCards.ProductParallelogramColumn
import com.bitio.ui.product.productsList.smallCards.ProductParallelogramGrid

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel ,
    navController: NavController,
) {
    val state by viewModel.favoriteUIState.collectAsState()
    FavoriteContent(
        state,
        onClickFavoriteButton = {},
        onClickBagButton = {}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteContent(
    state: FavoriteUiState,
    onClickFavoriteButton: () -> Unit,
    onClickBagButton: () -> Unit
) {

    var isGrid by remember {
        mutableStateOf(false)
    }

    var selectedGridColor =
        if (isGrid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

    var selectedLazyColumnColor =
        if (isGrid) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            CustomTopAppBar(
                selectedLazyColumnColor = selectedLazyColumnColor,
                selectedGridColor = selectedGridColor,
            ) {
                isGrid = it
            }
        }
    ) { paddingValue ->

        Crossfade(
            targetState = isGrid, modifier = Modifier.fillMaxWidth(),
            label = ""
        ) { targetIsGrid ->
            if (targetIsGrid) {
                ProductParallelogramGrid(products = List(50) { productWithOffer })
            } else {
                ProductParallelogramColumn(products = List(50) { productWithOffer })
            }
        }

    }
}

@Composable
fun CustomTopAppBar(
    selectedLazyColumnColor: Color,
    selectedGridColor: Color,
    onSelectedSortedItem: (Boolean) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Your Favorites",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = {

            IconButton(onClick = { onSelectedSortedItem(false) }) {
                Icon(
                    painter = painterResource(id = R.drawable.one_item),
                    contentDescription = "lazy column",
                    tint = selectedLazyColumnColor
                )
            }
            IconButton(onClick = { onSelectedSortedItem(true) }) {
                Icon(
                    painter = painterResource(id = R.drawable.multi_item),
                    contentDescription = "grid",
                    tint = selectedGridColor
                )
            }
        },
    )
}