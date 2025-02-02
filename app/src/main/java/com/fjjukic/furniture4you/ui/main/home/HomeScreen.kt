package com.fjjukic.furniture4you.ui.main.home

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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.model.BottomNavigationItem
import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.components.CategoryFilterItem
import com.fjjukic.furniture4you.ui.components.ProductItem
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProductClick: ((String) -> Unit) = {},
    onCartClick: (() -> Unit) = {},
    onSearchClick: (() -> Unit) = {},
    onNavigateToBottomBarRoute: ((String) -> Unit) = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = viewModel.homeState.collectAsState().value

    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                currentRoute = Screens.HomeSections.Home.route,
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.color_white))
        ) {
            HomeHeader(onSearchClick, onCartClick)
            CategoryFilter(
                categories = homeState.categories,
                selectedCategory = homeState.selectedCategory,
                onCategorySelected = { index ->
                    viewModel.setSelectedCategory(index)
                }
            )
            ProductList(homeState.products, onProductClick)
        }
    }
}

@Composable
fun BottomBarNavigation(
    currentRoute: String,
    onNavigateToBottomBarRoute: (String) -> Unit,
    tabs: List<String> = BottomNavigationItem().getRoutes()
) {
    val currentSection = tabs.first { it == currentRoute }

    Surface(shadowElevation = 8.dp) {
        NavigationBar(
            containerColor = colorResource(id = R.color.color_white),
        ) {
            BottomNavigationItem().bottomNavigationItems()
                .forEach { navigationItem ->
                    val selected =
                        navigationItem.route == currentSection
                    val icon =
                        if (selected) navigationItem.selectedIcon else navigationItem.unselectedIcon
                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            if (!selected && navigationItem.route == Screens.HomeSections.Notification.route) {
                                BadgedBox(badge = { Badge() }) {
                                    Icon(
                                        painterResource(icon),
                                        contentDescription = navigationItem.label
                                    )
                                }
                            } else {
                                Icon(
                                    painterResource(icon),
                                    contentDescription = navigationItem.label
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(id = R.color.color_white)
                        ),
                        onClick = {
                            onNavigateToBottomBarRoute(navigationItem.route)
                        },
                        modifier = Modifier.testTag(navigationItem.label)
                    )
                }
        }
    }
}

@Composable
fun HomeHeader(
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 16.dp, end = 16.dp)
            .testTag("homeHeader")
    ) {
        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                tint = colorResource(id = R.color.color_dark_gray),
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
                        color = colorResource(id = R.color.color_light_gray),
                    )
                ) {
                    append(stringResource(R.string.title_make_home))
                }
                append("\n")
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.color_medium_gray),
                    )
                ) {
                    append(stringResource(R.string.title_beautiful).uppercase())
                }
            }
        )
        IconButton(onClick = onCartClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                tint = colorResource(id = R.color.color_dark_gray),
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
        modifier = modifier
            .padding(top = 20.dp, bottom = 8.dp)
            .testTag("categoryFilter"),
        state = rememberLazyListState(),
        userScrollEnabled = true,
        contentPadding = PaddingValues(start = 20.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryFilterItem(
                title = item.title,
                isSelected = index == selectedCategory,
                imageResId = item.imageResId,
                onCategorySelect = { onCategorySelected(index) },
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .testTag("productList"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 12.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(products) { product ->
            ProductItem(
                product,
                onProductClick = onProductClick
            )
        }
    }
}