package com.fjjukic.furniture4you.ui.main.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.common.showFeatureNotAvailable
import com.fjjukic.furniture4you.ui.components.CartItem
import com.fjjukic.furniture4you.ui.main.home.BottomBarNavigation
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Preview
@Composable
fun FavoritePreview() {
    FavoriteScreen(
        onProductClick = {},
        onSearchClick = {},
        onCartClick = {},
        onNavigateToBottomBarRoute = {})
}

@Composable
fun FavoriteScreen(
    onProductClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onNavigateToBottomBarRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        modifier,
        containerColor = colorResource(id = R.color.color_transparent),
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_favorites),
                startIconResId = R.drawable.ic_search,
                endIconResId = R.drawable.ic_cart,
                onStartActionClick = onSearchClick,
                onEndActionClick = onCartClick,
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            BottomBarNavigation(
                currentRoute = Screens.HomeSections.Favorites.route,
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
    ) { paddingValues ->
        FavoriteScreenContent(
            onProductClick = onProductClick,
            onCartClick = onCartClick,
            onRemoveClick = viewModel::onRemoveClick,
            products = state.products,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun FavoriteScreenContent(
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onRemoveClick: (String) -> Unit,
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(modifier.background(colorResource(id = R.color.color_white))) {
        if (products.isEmpty()) {
            NoFavorites()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 14.dp,
                    bottom = 100.dp
                )
            ) {
                itemsIndexed(products) { index, product ->
                    key(product.id) {
                        FavoriteItem(
                            title = product.title,
                            price = product.price,
                            imageResId = product.imageResId,
                            onProductClick = {
                                onProductClick(product.id)
                            },
                            onCartClick = onCartClick,
                            onRemoveClick = {
                                onRemoveClick(product.id)
                            }
                        )
                        if (index != products.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = colorResource(id = R.color.color_tinted_white),
                                thickness = 1.dp
                            )
                        }
                    }
                }

            }
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
                    .height(60.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(colorResource(id = R.color.color_transparent)),
                onClick = {
                    showFeatureNotAvailable(context)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.btn_add_all_to_my_cart),
                    style = GelatioTypography.bodyMedium,
                    color = colorResource(id = R.color.color_white)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(
        title = stringResource(id = R.string.placeholder_name),
        startIconResId = R.drawable.ic_search,
        endIconResId = R.drawable.ic_favorite,
        onStartActionClick = {},
        onEndActionClick = {})
}

@Composable
fun Header(
    title: String,
    startIconResId: Int,
    endIconResId: Int,
    onStartActionClick: () -> Unit,
    onEndActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
        ) {
            IconButton(onClick = onStartActionClick) {
                Icon(
                    painter = painterResource(id = startIconResId),
                    tint = colorResource(id = R.color.color_dark_gray),
                    contentDescription = stringResource(R.string.content_desc_action_start_icon)
                )
            }
            Text(
                text = title,
                style = NunitoSansTypography.titleSmall,
                color = colorResource(id = R.color.color_medium_gray),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            IconButton(onClick = onEndActionClick) {
                Icon(
                    painter = painterResource(id = endIconResId),
                    tint = colorResource(id = R.color.color_dark_gray),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
        }
        HorizontalDivider(
            color = colorResource(id = R.color.color_tinted_white),
            thickness = 1.dp
        )
    }
}

@Preview
@Composable
fun FavoriteItemPreview() {
    val product = MockRepository.getProducts().first()
    FavoriteItem(
        title = product.title,
        price = product.price,
        imageResId = R.drawable.img_minimal_stand,
        onProductClick = {},
        onCartClick = {},
        onRemoveClick = {},
    )
}

@Composable
fun FavoriteItem(
    title: String,
    price: String,
    imageResId: Int,
    onProductClick: () -> Unit,
    onCartClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onProductClick
            )
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = imageResId),
            contentDescription = stringResource(R.string.content_desc_product),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp)
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = title,
                color = colorResource(id = R.color.color_light_gray),
                style = NunitoSansTypography.labelSmall,
            )
            Text(
                text = stringResource(id = R.string.title_product_price, price),
                color = colorResource(id = R.color.color_dark_gray),
                style = NunitoSansTypography.titleSmall,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onRemoveClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
            CartItem(
                onItemSelect = onCartClick,
                modifier = Modifier
                    .wrapContentSize(),
                itemColor = colorResource(id = R.color.color_medium_gray),
                itemBackground = colorResource(id = R.color.bg_favorite_cart_item),
                itemRadius = 12.dp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NoFavoritesPreview() {
    NoFavorites()
}

@Composable
fun NoFavorites(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.img_no_result),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.title_no_favorites),
            fontSize = 16.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.color_dark_gray),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(id = R.string.label_check_products),
            fontSize = 16.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.Thin,
            color = colorResource(id = R.color.color_gray),
        )
    }
}