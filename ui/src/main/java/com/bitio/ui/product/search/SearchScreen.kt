package com.bitio.ui.product.search

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bitio.ui.product.search.filtersBottomSheet.FiltersBottomSheet
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchScreen(navController: NavController) {
    val isGridDisplayMode = remember { mutableStateOf(false) }
    val viewModel = koinViewModel<SearchViewModel>()

    val data = viewModel.flow.collectAsLazyPagingItems()

    var isExpanded = remember { mutableStateOf(false) }

    if (isExpanded.value)
        FiltersBottomSheet(
            isExpanded,
            viewModel.categories,
            viewModel.availableSizes,
            viewModel.priceRange
        ) {}
    TextButton(onClick = { isExpanded.value = !isExpanded.value }) {
        Text(text = "aaa")
    }


}
