package com.bitio.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.ui.R
import com.bitio.ui.theme.WhiteColorWithOpacity

@Composable
fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    isFavoriteState: MutableState<Boolean>, onClick: () -> Unit
) {
    val iconId = if (isFavoriteState.value) R.drawable.ic_heart_filled else R.drawable.ic_heart
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClick() },
            painter = painterResource(id = iconId),
            contentDescription = "add to cart button",
            tint = Color.Unspecified

        )

    }
}


@Composable
fun FavoriteIconButtonCircularBg(
    modifier: Modifier = Modifier,
    isFavoriteState: MutableState<Boolean>,
    productId: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(WhiteColorWithOpacity)
            .clickable { onClick(productId) },
        contentAlignment = Alignment.Center
    ) {
        val iconId = if (isFavoriteState.value) R.drawable.ic_heart_filled else R.drawable.ic_heart
        Icon(modifier= Modifier.size(14.dp),
            painter = painterResource(id = iconId),
            contentDescription = "add to cart button",
            tint = Color.Unspecified

        )
    }
}

@Composable
fun CartIconButton(modifier: Modifier = Modifier, productId: String, onClick: (String) -> Unit = {}) {
    Icon(
        modifier = modifier.requiredSize(24.dp).clickable { onClick(productId) },
        painter = painterResource(id = R.drawable.ic_bag),
        contentDescription = "add to cart button",
        tint = Color.Unspecified
    )
}

@Composable
fun CartIconButtonCircularBg(onClick: () -> Unit = {}) {
    val modifier = remember {
        Modifier
            .size(24.dp)
            .clip(CircleShape)
            // Todo(compose bug unnecessary recomposition)
            .clickable { onClick() }
            .background(Color.White.copy(alpha = 0.15f))
    }
    Box(
        modifier = modifier,

        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_bag),
            contentDescription = "add to cart button",
            tint = Color.Unspecified
        )
    }
}


