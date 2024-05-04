package com.fjjukic.furniture4you.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.home.model.CategoryFilterItem
import com.fjjukic.furniture4you.ui.home.model.ProductItem
import com.fjjukic.furniture4you.ui.model.Product
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun HomePreview() {
    Home(HomeViewModel(), onProductClicked = {}, onCartClicked = {})
}

@Composable
fun Home(
    viewModel: HomeViewModel,
    onProductClicked: (String) -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeHeader(onCartClicked, modifier = Modifier.padding(top = 6.dp))
        CategoryFilter(homeState.categories, selectedCategory, { index ->
            viewModel.setSelectedCategory(index)
        })
        ProductList(homeState.products, onProductClicked)
    }
}

@Composable
fun HomeHeader(onCartClick: () -> Unit, modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = 20.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.content_desc_search)
        )
        BasicText(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.Light,
                        color = colorResource(id = R.color.light_gray),
                    )
                ) {
                    append("Make home")
                }
                append("\n")
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.medium_gray),
                    )
                ) {
                    append("Beautiful".uppercase())
                }
            }
        )
        Image(
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onCartClick
                ),
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = stringResource(id = R.string.content_desc_cart)
        )
    }
}

@Composable
fun CategoryFilter(
    categories: List<CategoryItemModel>,
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(top = 20.dp, bottom = 8.dp),
        state = rememberLazyListState(),
        userScrollEnabled = true,
        contentPadding = PaddingValues(start = 20.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryFilterItem(
                title = item.title,
                isSelected = index == selectedCategory,
                imageResId = item.imageResId,
                onCategorySelected = { onCategorySelected.invoke(index) },
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 12.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(products) { product ->
            ProductItem(
                product,
                onProductClicked = onProductClicked
            )
        }
    }
}

class CategoryItemModel(
    val title: String,
    val imageResId: Int
)