package com.bitio.ui.product.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.Brand
import com.bitio.productscomponent.domain.entities.products.Product
import com.bitio.ui.R
import com.bitio.ui.product.CartIconButton
import com.bitio.ui.product.FavoriteIconButtonCircularBg
import com.bitio.ui.shared.AsyncDescribedImage
import com.bitio.ui.shared.VerticalSpacer2Dp

@Composable
fun BrandRow(
    brand: Brand,
    products: List<Product>,
    onSeeAllClicked: (String) -> Unit,
    onCardClicked: (String) -> Unit,
    onAddToFavoriteClicked: (String) -> Unit,
    onAddToCartClicked: (String) -> Unit,
) {
    ItemsTitleComponent(name = brand.name) { onSeeAllClicked(brand.id) }
    LazyRow(contentPadding = PaddingValues(start = 24.dp, end = 8.dp)) {
        items(products.size, key = { it }, contentType = { Product::class }) {

            ProductCard(
                products[it],
                onCardClicked = onCardClicked,
                onAddToCartClicked = onAddToCartClicked,
                onAddToFavoriteClicked = onAddToFavoriteClicked
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onCardClicked: (String) -> Unit,
    onAddToCartClicked: (String) -> Unit,
    onAddToFavoriteClicked: (String) -> Unit
) {
    Box(modifier = Modifier
        .padding(end = 16.dp)
        .size(150.dp, 200.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onCardClicked(product.id) }) {
        AsyncDescribedImage(imageLink = product.image)
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            val isFav = remember { mutableStateOf(true) }

            FavoriteIconButtonCircularBg(
                modifier = Modifier.padding(12.dp), isFav,
                productId = product.id
            ) { onAddToFavoriteClicked(product.id) }
            ProductCardDetailsCurve(product, onAddToCartClicked)

        }

    }

}

@Composable
fun ProductCardDetailsCurve(product: Product, onAddToCartClicked: (String) -> Unit) {
    Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.product_card_shape),
            contentDescription = null,
            contentScale = ContentScale.FillWidth

        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = product.title, style = MaterialTheme.typography.bodyMedium
                )
                VerticalSpacer2Dp()
                Text(
                    text = "$" + product.price.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            CartIconButton(productId = product.id, onClick = onAddToCartClicked)
        }
    }
}

