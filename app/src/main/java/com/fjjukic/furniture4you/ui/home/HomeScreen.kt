package com.fjjukic.furniture4you.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.components.CategoryItem
import com.fjjukic.furniture4you.ui.components.ProductItem
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.model.Product
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onProductClicked = {})
}

@Composable
fun HomeScreen(onProductClicked: (String) -> Unit, modifier: Modifier = Modifier) {
    val categories: List<CategoryItemModel> = MockRepository.getCategories()
    val products: List<Product> = MockRepository.getProducts()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
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
                            color = Color(0xFF808080),
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
                            color = Color(0xFF303030),
                        )
                    ) {
                        append("Beautiful".uppercase())
                    }
                }
            )
            Image(
                modifier = Modifier.padding(end = 20.dp),
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = stringResource(id = R.string.content_desc_cart)
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
            state = rememberLazyListState(),
            userScrollEnabled = true
        ) {
            itemsIndexed(categories) { index, item ->
                val startPadding = if (index == 0) 20.dp else 0.dp

                CategoryItem(
                    modifier = Modifier.padding(start = startPadding, end = 20.dp),
                    title = item.title,
                    imageResId = item.imageResId,
                    isSelected = index == selectedIndex,
                    onItemSelected = {
                        selectedIndex = index
                    }
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 12.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(products) { item ->
                ProductItem(
                    id = item.id,
                    title = item.title,
                    price = item.price,
                    imageResId = item.imageResId,
                    onProductClicked = onProductClicked
                )
            }
        }
    }
}

class CategoryItemModel(
    val title: String,
    val imageResId: Int
)