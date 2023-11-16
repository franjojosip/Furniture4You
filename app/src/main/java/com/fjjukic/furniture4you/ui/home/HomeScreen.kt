package com.fjjukic.furniture4you.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
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
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val categories: List<CategoryItemModel> = getCategories()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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
            modifier = Modifier.padding(top = 20.dp),
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
    }
}

class CategoryItemModel(
    val title: String,
    val imageResId: Int
)

fun getCategories(): List<CategoryItemModel> {
    return listOf(
        CategoryItemModel(
            title = "Popular",
            imageResId = R.drawable.ic_star
        ),
        CategoryItemModel(
            title = "Chair",
            imageResId = R.drawable.ic_chair
        ),
        CategoryItemModel(
            title = "Table",
            imageResId = R.drawable.ic_table
        ),
        CategoryItemModel(
            title = "Armchair",
            imageResId = R.drawable.ic_sofa
        ),
        CategoryItemModel(
            title = "Bed",
            imageResId = R.drawable.ic_bed
        ),
        CategoryItemModel(
            title = "Lamp",
            imageResId = R.drawable.ic_lamp
        )
    )
}