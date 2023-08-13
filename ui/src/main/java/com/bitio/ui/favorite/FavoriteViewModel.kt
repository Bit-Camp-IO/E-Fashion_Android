package com.bitio.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _favoriteUIState = MutableStateFlow(FavoriteUiState())
    val favoriteUIState: StateFlow<FavoriteUiState> = _favoriteUIState.asStateFlow()

    init {
        getFavoritesItems()
    }

    private fun getFavoritesItems() {
        viewModelScope.launch {
            _favoriteUIState.update { it.copy(isLoading = true) }
            _favoriteUIState.update {
                it.copy(
                    isLoading = false, state = listOf(
                        Favorite(
                            image = "https://th.bing.com/th/id/OIP.VFya5WgweuE8FE3i66DiTQAAAA?pid=ImgDet&rs=1",
                            title = "Black Dress",
                            price = "200"
                        ),
                        Favorite(
                            image = "https://cdnc.lystit.com/photos/9789-2014/02/19/lauren-ralph-lauren-orange-plus-size-tab-sleeve-linen-shirt-product-1-17781787-1-361435555-normal.jpeg",
                            title = "Orange Shirt",
                            price = "150"
                        ),
                        Favorite(
                            image = "https://th.bing.com/th/id/OIP.VFya5WgweuE8FE3i66DiTQAAAA?pid=ImgDet&rs=1",
                            title = "Black Dress",
                            price = "200"
                        ),
                        Favorite(
                            image = "https://cdnc.lystit.com/photos/9789-2014/02/19/lauren-ralph-lauren-orange-plus-size-tab-sleeve-linen-shirt-product-1-17781787-1-361435555-normal.jpeg",
                            title = "Orange Shirt",
                            price = "150"
                        ),
                        Favorite(
                            image = "https://th.bing.com/th/id/OIP.VFya5WgweuE8FE3i66DiTQAAAA?pid=ImgDet&rs=1",
                            title = "Black Dress",
                            price = "200"
                        ),
                        Favorite(
                            image = "https://cdnc.lystit.com/photos/9789-2014/02/19/lauren-ralph-lauren-orange-plus-size-tab-sleeve-linen-shirt-product-1-17781787-1-361435555-normal.jpeg",
                            title = "Orange Shirt",
                            price = "150"
                        )
                    )
                )
            }
        }
    }
}