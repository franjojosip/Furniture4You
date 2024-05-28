package com.fjjukic.furniture4you.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.home.model.CategoryFilterItem
import com.fjjukic.furniture4you.ui.home.model.CategoryItem
import com.fjjukic.furniture4you.ui.home.model.ProductItem
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun HomePreview() {
    Home(HomeViewModel(), onProductClicked = {}, onCartClick = {}, onSearchClick = {})
}

@Composable
fun Home(
    viewModel: HomeViewModel,
    onProductClicked: (String) -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        HomeHeader(onSearchClick, onCartClick)
        CategoryFilter(homeState.categories, selectedCategory, { index ->
            viewModel.setSelectedCategory(index)
        })
        ProductList(homeState.products, onProductClicked)
    }
}

@Composable
fun HomeHeader(onSearchClick: () -> Unit, onCartClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 16.dp, end = 16.dp)
    ) {
        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_start_icon)
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 6.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.Light,
                        color = colorResource(id = R.color.light_gray),
                    )
                ) {
                    append(stringResource(R.string.make_home))
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
                    append(stringResource(R.string.beautiful).uppercase())
                }
            }
        )
        IconButton(onClick = onCartClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_end_icon)
            )
        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<CategoryItem>,
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