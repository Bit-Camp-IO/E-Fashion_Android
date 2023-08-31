package com.bitio.ui.product.home.composables

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bitio.ui.R
import com.bitio.ui.product.models.UiCategory
import com.bitio.ui.shared.HorizontalSpacer2Dp
import com.bitio.ui.shared.HorizontalSpacer4Dp
import com.bitio.ui.shared.HorizontalSpacer8Dp
import com.bitio.ui.shared.VerticalSpacer8Dp
import com.bitio.ui.theme.PeacockBlue
import kotlinx.coroutines.flow.StateFlow


@Composable
fun CategoriesRow(
    categoriesFlow: StateFlow<List<UiCategory>>,
    selectGenderList: List<MutableState<Boolean>>,
    onGenderCardClicked: (Int) -> Unit,
    onApplyFilterClicked:()->Unit,
) {
    val scrollState = rememberScrollState()
    Column {
        CategoryTitleComponent(onApplyFilterClicked)
        Row(
            Modifier
                .horizontalScroll(scrollState)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GenderCategoryRow(selectGenderList, scrollState, onGenderCardClicked)
            ProductCategoriesRow(categoriesFlow)

        }
    }
}


@Composable
fun GenderCategoryRow(
    isSelectedList: List<MutableState<Boolean>>,
    scrollState: ScrollState,
    onClick: (Int) -> Unit
) {


    Row(
        Modifier.graphicsLayer {

            translationX = 0.5f * scrollState.value
        },
        verticalAlignment = Alignment.CenterVertically
    ) {

        HorizontalSpacer8Dp()
        Row(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
        ) {

            HorizontalSpacer8Dp()

            GenderCategoryCard("Men", isSelectedList[0]) { onClick(0) }
            GenderCategoryCard("Women", isSelectedList[1]) { onClick(1) }


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
fun GenderCategoryCard(name: String, isSelected: MutableState<Boolean>, onClick: () -> Unit) {
    CategoryCard(
        id = "",
        name = name,
        image = myImage,
        isSelected,
    ) { onClick() }
}

@Composable
fun ProductCategoriesRow(categoriesFlow: StateFlow<List<UiCategory>>) {
    val categories by categoriesFlow.collectAsState()
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
        for (category in categories) {
            ProductCategoryCard(category = category)

        }
        HorizontalSpacer8Dp()
    }
}

@Composable
fun ProductCategoryCard(category: UiCategory) {
    CategoryCard(
        id = category.id,
        name = category.name,
        image = category.image,
        isSelected = category.selectedState,
        onClick = category.onCategoryClicked,
    )
}

@Composable
fun CategoryCard(
    id: String,
    name: String,
    image: String,
    isSelected: MutableState<Boolean>,
    onClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { isSelected.value = !isSelected.value; onClick(id) }
            .padding(all = 8.dp)
    ) {
        val value = isSelected.value
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .then(
                    if (isSelected.value)
                        Modifier.border(
                            border = BorderStroke(
                                2.dp,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            shape = CircleShape
                        ) else Modifier
                ),
            contentScale = ContentScale.FillBounds
        )
        VerticalSpacer8Dp()

        Text(text = name)


    }
}

@Composable
fun CategoryTitleComponent(onClick: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Filter", style = MaterialTheme.typography.bodyMedium)
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = Color.White
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.ic_filter_8), contentDescription = null)
                HorizontalSpacer2Dp()
                Text(text = "Apply", style = MaterialTheme.typography.labelSmall)
            }
        }


    }
}

const val myImage =
    "https://previews.123rf.com/images/f8studio/f8studio1707/f8studio170701400/82842066-young-girl-in-stylish-clothes-posing-in-the-city-street.jpg"