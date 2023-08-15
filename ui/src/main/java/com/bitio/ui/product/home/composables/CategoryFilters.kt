package com.bitio.ui.product.home.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bitio.productscomponent.domain.entities.categories.GenderCategory
import com.bitio.productscomponent.domain.entities.categories.ProductCategory
import com.bitio.ui.shared.HorizontalSpacer4Dp
import com.bitio.ui.shared.HorizontalSpacer8Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.PeacockBlue


@Composable
fun CategoriesRow() {
    val scrollState = rememberScrollState()
    Column {
        ItemsTitleComponent(name = "Filter") {}
        Row(Modifier.horizontalScroll(scrollState).padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
            GenderCategoryRow(genderCategories, scrollState)
            ProductCategoriesRow(productCategories = productCategories)

        }
    }
}

object CachedOffsets {
    private var offSet: Float? = null
    private var offSet2: Float? = null


    fun getFirstCachedOffset(currentOffset: Float): Float {
        if (offSet == null) offSet = currentOffset
        return offSet!!
    }

    fun getSecCachedOffset(currentOffset: Float): Float {
        if (offSet2 == null) offSet2 = currentOffset
        return offSet2!!
    }
}

fun getXTranslation(listState: LazyListState): Float {


    return try {
        val offset1 =
            CachedOffsets.getFirstCachedOffset(listState.layoutInfo.visibleItemsInfo[0].offset.toFloat())
        val offset2 =
            CachedOffsets.getSecCachedOffset(listState.layoutInfo.visibleItemsInfo[1].offset.toFloat())

        offset1 - 0.5f * (listState.layoutInfo.visibleItemsInfo[1].offset.toFloat() - offset2)
    } catch (e: IndexOutOfBoundsException) {
        CachedOffsets.getFirstCachedOffset(0f) +
                -listState.layoutInfo.visibleItemsInfo[0].offset
    }

}

@Composable
fun GenderCategoryRow(genderCategories: List<GenderCategory>, scrollState: ScrollState) {


        Row(Modifier.graphicsLayer {
            Log.d("aaa",scrollState.value.toString())
            translationX = 0.5f* scrollState.value  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoButton {}
            HorizontalSpacer8Dp()
            Row(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
            ) {

                HorizontalSpacer8Dp()
                for (genderCategory in genderCategories) {
                    GenderCategoryCard(genderCategory = genderCategory)
                }
                HorizontalSpacer8Dp()
            }

    }
}

@Composable
fun GoButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) { Text(text = "Go") }
}

@Composable
fun GenderCategoryCard(genderCategory: GenderCategory) {
    CategoryCard(
        id = genderCategory.id,
        name = genderCategory.name,
        image = genderCategory.image,
    ) {

    }
}

@Composable
fun ProductCategoriesRow(productCategories: List<ProductCategory>) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(6.dp, 40.dp)
                .background(color = PeacockBlue, shape = RoundedCornerShape(16.dp))
        )
        HorizontalSpacer4Dp()
        for (productCategory in productCategories) {
            ProductCategoryCard(productCategory = productCategory)

        }
        HorizontalSpacer8Dp()
    }
}

@Composable
fun ProductCategoryCard(productCategory: ProductCategory) {
    CategoryCard(
        id = productCategory.id,
        name = productCategory.name,
        image = productCategory.image,
    ) {}
}

@Composable
fun CategoryCard(id: Int, name: String, image: String, onClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick(id) }
            .padding(all = 8.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .border(
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.secondary),
                    shape = CircleShape
                ),
            contentScale = ContentScale.FillBounds
        )
        VerticalSpacer8Dp()

        Text(text = name)


    }
}


val genderCategory = object : GenderCategory {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Male"
    override val image: String
        get() = myImage
}
val genderCategories = List(2) { genderCategory }
val productCategory = object : ProductCategory {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Skirt"
    override val image: String
        get() = myImage
}
val productCategories = List(10) { productCategory }


const val myImage ="https://picsum.photos/100.webp"
 //   "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"