@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitio.ui.favorite

import android.annotation.SuppressLint
import android.media.session.PlaybackState.CustomAction
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bitio.ui.R
import com.bitio.ui.theme.Porcelain
import com.bitio.ui.theme.textStyles.AppThemeTextStyles

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
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
                GridLayoutContent(
                    favorites = state.state,
                    calculateTopPadding = paddingValue.calculateTopPadding(),
                    onClickFavoriteButton = onClickFavoriteButton,
                    onClickBagButton = onClickBagButton
                )
            } else {
                LazyColumnContent(
                    favorites = state.state,
                    calculateTopPadding = paddingValue.calculateTopPadding(),
                    onClickFavoriteButton = onClickFavoriteButton,
                    onClickBagButton = onClickBagButton
                )
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
                style = AppThemeTextStyles(MaterialTheme.colorScheme.onBackground).titleMedium,
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

@Composable
private fun GridLayoutContent(
    favorites: List<Favorite>,
    calculateTopPadding: Dp,
    onClickFavoriteButton: () -> Unit,
    onClickBagButton: () -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = calculateTopPadding, start = 24.dp, end = 24.dp)
    ) {
        items(favorites.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = favorites[index].image),
                    contentDescription = favorites[index].title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = if (index % 2 == 0) Alignment.Start else Alignment.End
                ) {

                    CustomIconButton(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0x26FFFFFF)),
                        painter = painterResource(id = R.drawable.outline_heart)
                    ) {
                        onClickFavoriteButton()
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = if (index % 2 == 0) R.drawable.shape_left_top_card_small else R.drawable.shape_right_top_card_small),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds
                        )


                        CustomIconButton(
                            modifier = Modifier
                                .align(if (index % 2 == 0) Alignment.CenterStart else Alignment.CenterEnd)
                                .background(Color.Transparent),
                            painter = painterResource(id = R.drawable.bag)
                        ) {
                            onClickBagButton()
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = favorites[index].title,
                                style = AppThemeTextStyles(Porcelain).bodySmall
                            )
                            Text(
                                text = "$${favorites[index].price}",
                                style = AppThemeTextStyles(Porcelain).bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LazyColumnContent(
    favorites: List<Favorite>,
    calculateTopPadding: Dp,
    onClickFavoriteButton: () -> Unit,
    onClickBagButton: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(top = calculateTopPadding, start = 24.dp, end = 24.dp)
    ) {
        items(favorites.size) { index ->
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .wrapContentSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = favorites[index].image),
                    contentDescription = favorites[index].title,
                    modifier = Modifier
                        .fillMaxSize(), contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    CustomIconButton(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0x26FFFFFF))
                            .align(Alignment.End),
                        painter = painterResource(id = R.drawable.outline_heart)
                    ) {
                        onClickFavoriteButton()
                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(114.dp)
                            .wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.shape_top_card_large),
                            contentDescription = favorites[index].title,
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds
                        )

                        Column(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = favorites[index].title,
                                style = AppThemeTextStyles(Porcelain).titleLarge
                            )
                            Text(
                                text = "$${favorites[index].price}",
                                style = AppThemeTextStyles(Porcelain).titleSmall,
                            )
                        }
                        CustomIconButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color.Transparent)
                                .align(Alignment.CenterEnd),
                            painter = painterResource(id = R.drawable.bag)
                        ) {
                            onClickBagButton()
                        }

                    }
                }
            }
        }
    }

}


@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(18.dp),
            tint = tint,
        )
    }
}


private val firstIndexOfLazyCardShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val pathData =
            "M0.5" +
                    " ${size.height * 0.16648}C0.5" +
                    " ${size.height * 0.05124}" +
                    " ${size.width * 0.07453} 0" +
                    " ${size.width * 0.11034} 0H" +
                    "${size.width * 0.94507}C" +
                    "${size.width * 0.97126} 0 " +
                    "${size.width} " +
                    "${size.height * 0.05115} " +
                    "${size.width} " +
                    "${size.height * 0.16648}V" +
                    "${size.height * 0.99914}L0.5 " +
                    "${size.height * 0.85606}V" +
                    "${size.height * 0.16648}" +
                    "Z"

        return Outline.Generic(
            PathParser.createPathFromPathData(pathData).asComposePath()
        )
    }
}

private val otherIndexesOfLazyCardShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val pathData =
            "M0 ${size.height * 0}L${size.width} ${size.height * 0.13349}V${size.height * 0.92359}L0 ${size.height * 0.78708}L0 ${size.height * 0}"

        return Outline.Generic(
            PathParser.createPathFromPathData(pathData).asComposePath()
        )
    }
}


private val firstRightGridShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData =
            "M0.5" +
                    " ${size.height * 0.16648}C0.5" +
                    " ${size.height * 0.05124}" +
                    " ${size.width * 0.07453} 0" +
                    " ${size.width * 0.11034} 0H" +
                    "${size.width * 0.94507}C" +
                    "${size.width * 0.97126} 0 " +
                    "${size.width} " +
                    "${size.height * 0.05115} " +
                    "${size.width} " +
                    "${size.height * 0.16648}V" +
                    "${size.height * 0.99914}L0.5 " +
                    "${size.height * 0.85606}V" +
                    "${size.height * 0.16648}" +
                    "Z"

        return Outline.Generic(
            PathParser.createPathFromPathData(pathData).asComposePath()
        )
    }
}
private val othersRightGridShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData =
            "M0 0L${size.width} 0V${size.height}L0 ${size.height}"

        return Outline.Generic(
            PathParser.createPathFromPathData(pathData).asComposePath()
        )
    }
}

private val othersLeftGridShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData =
            "M0 ${size.height * 0.92359}L${size.width} ${size.height * 0.78708}V${size.height * 0.13349}L0 ${size.height * 0}"

        return Outline.Generic(
            PathParser.createPathFromPathData(pathData).asComposePath()
        )
    }
}
