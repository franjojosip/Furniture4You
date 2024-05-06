package com.fjjukic.furniture4you.ui.favorite

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.components.CartItem
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun FavoritePreview() {
    Favorite(FavoriteViewModel(), {}, {}, {})
}

@Composable
fun Favorite(
    viewModel: FavoriteViewModel,
    onProductClicked: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.products.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        containerColor = Color.Transparent,
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_favorites),
                startIconResId = R.drawable.ic_search,
                endIconResId = R.drawable.ic_cart,
                onStartActionClick = onSearchClicked,
                onEndActionClick = onCartClicked,
                modifier = Modifier.background(Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray)),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.new_feature_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_add_all_to_my_cart),
                        style = GelatioTypography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        FavoriteScreenContent(
            onProductClicked = onProductClicked,
            onCartClicked = onCartClicked,
            onRemoveClicked = viewModel::onRemoveClicked,
            products = products,
            bottomPaddingValue = paddingValues.calculateBottomPadding(),
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        )
    }
}

@Composable
fun FavoriteScreenContent(
    onProductClicked: (String) -> Unit,
    onCartClicked: () -> Unit,
    onRemoveClicked: (String) -> Unit,
    products: List<Product>,
    bottomPaddingValue: Dp,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(Color.White),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 6.dp,
            bottom = bottomPaddingValue + 12.dp
        )
    ) {
        itemsIndexed(products) { index, product ->
            key(product.id) {
                FavoriteItem(
                    onProductClicked = {
                        onProductClicked(product.id)
                    },
                    onCartClicked = onCartClicked,
                    onRemoveClicked = {
                        onRemoveClicked(product.id)
                    },
                    product.imageResId,
                    product.price
                )
                if (index != products.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = colorResource(id = R.color.tinted_white),
                        thickness = 1.dp
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(
        title = "My cart",
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        IconButton(onClick = onStartActionClick) {
            Icon(
                painter = painterResource(id = startIconResId),
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_start_icon)
            )
        }
        Text(
            text = title,
            style = NunitoSansTypography.titleSmall,
            color = colorResource(id = R.color.medium_gray),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = onEndActionClick) {
            Icon(
                painter = painterResource(id = endIconResId),
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_end_icon)
            )
        }
    }
}

@Preview
@Composable
fun FavoriteItemPreview() {
    FavoriteItem(
        onProductClicked = {},
        onCartClicked = {},
        onRemoveClicked = {},
        R.drawable.img_minimal_stand,
        "24.99"
    )
}

@Composable
fun FavoriteItem(
    onProductClicked: () -> Unit,
    onCartClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
    imageResId: Int, price: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onProductClicked
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
                text = "Coffee Table",
                color = colorResource(id = R.color.light_gray),
                style = NunitoSansTypography.labelSmall,
            )
            Text(
                text = stringResource(id = R.string.product_price_title, price),
                color = colorResource(id = R.color.dark_gray),
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
                onClick = onRemoveClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
            CartItem(
                onItemSelected = onCartClicked,
                modifier = Modifier
                    .wrapContentSize(),
                itemColor = colorResource(id = R.color.medium_gray),
                itemBackground = colorResource(id = R.color.favorite_cart_item_background),
                itemRadius = 12.dp
            )
        }
    }
}