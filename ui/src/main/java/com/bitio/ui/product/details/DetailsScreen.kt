package com.bitio.ui.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bitio.productscomponent.domain.entities.products.ProductDetails
import com.bitio.productscomponent.domain.entities.selectable.Option
import com.bitio.productscomponent.domain.entities.selectable.SelectableProperty
import com.bitio.ui.product.details.composables.DetailsCard
import com.bitio.ui.product.details.composables.ImagesPager

@Composable
fun DetailsScreen() {
    val productDetails = productDetails
    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy((-64).dp)
    ) {
        ImagesPager(
            modifier = Modifier.graphicsLayer {
                translationY = 0.5f * scrollState.value
                alpha =1-(0.75f* scrollState.value.toFloat()/scrollState.maxValue)
            },
            images = productDetails.images
        )
        DetailsCard(productDetails = productDetails)

    }
}

val productDetails = object : ProductDetails {
    override val id: Int
        get() = 1
    override val name: String
        get() = "yellowJacket"
    override val images: List<String>
        get() = listOf(myImage, myImage, myImage, myImage)
    override val price: Float
        get() = 180f
    override val description: String
        get() = "i don't know what to write i don't know what to write i don't know what to write i don't know what to write i don't know what to write "
    override val brandId: Int
        get() = 100
    override val brandName: String
        get() = "bla bla bla "
    override val selectableProperties: List<SelectableProperty>
        get() = listOf(myColorsSelectable, mySizeSelectable)

}
val myColorsSelectable = object : SelectableProperty {
    override val id: Int
        get() = 1
    override val name: String
        get() = "Color"
    override val options: List<Option>
        get() = listOf(
            option1,
            option2,
            option3,
            option1,
            option2,
            option3,
            option1,
            option2,
            option3,
            option1,
            option2,
            option3
        )
}
val option1 = object : Option {
    override val id: Int
        get() = 1
    override val name: String
        get() = "FF00E0FF"

}
val option2 = object : Option {
    override val id: Int
        get() = 2
    override val name: String
        get() = "FFFFE0FF"

}
val option3 = object : Option {
    override val id: Int
        get() = 2
    override val name: String
        get() = "FF006494"

}
val mySizeSelectable = object : SelectableProperty {
    override val id: Int
        get() = 4
    override val name: String
        get() = "Size"
    override val options: List<Option>
        get() = listOf(
            option4, option5, option6,
            option4, option5, option6,
            option4, option5, option6,
            option4, option5, option6,
        )

}

val option4 = object : Option {
    override val id: Int
        get() = 2
    override val name: String
        get() = "S"

}
val option5 = object : Option {
    override val id: Int
        get() = 2
    override val name: String
        get() = "L"

}
val option6 = object : Option {
    override val id: Int
        get() = 1
    override val name: String
        get() = "XL"

}
val myImage =
    "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"