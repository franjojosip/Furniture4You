package com.fjjukic.furniture4you.ui.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.productdetail.model.ColorPalette
import com.fjjukic.furniture4you.ui.productdetail.model.ProductDetailCartDialog
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ProductPreviewScreenPreview() {
    ProductDetail(viewModel = ProductDetailViewModel(), {}, {})
}

@Composable
fun ProductDetail(
    viewModel: ProductDetailViewModel,
    onBackClicked: () -> Unit,
    onNavigateToCartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val productState by viewModel.productState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        topBar = {
            BackButton {
                onBackClicked()
            }
        },
        bottomBar = {
            ProductBottomButtons(onNavigateToCartClicked)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            ImageSlider(productState.selectedProductDetail.imageUrl)
            ProductContent(
                productState.selectedProductDetail,
                productState.counter,
                viewModel::onIncrementClick,
                viewModel::onDecrementClick,
                modifier = Modifier
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .padding(start = 24.dp, top = 16.dp),
        shape = RoundedCornerShape(6.dp),
        shadowElevation = 8.dp
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(10.dp)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.content_desc_back_btn)
            )
        }
    }
}

@Composable
fun ProductBottomButtons(onNavigateToCartClicked: () -> Unit, modifier: Modifier = Modifier) {
    var isFavoriteSelected by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        ProductDetailCartDialog(
            onContinueClicked = {
                openDialog = false
            },
            onDismissClicked = {
                openDialog = false
                onNavigateToCartClicked()
            }
        )
    }

    Row(modifier.padding(16.dp)) {
        IconButton(
            onClick = {
                isFavoriteSelected = !isFavoriteSelected
            },
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.tinted_white))
        ) {
            Icon(
                painter = painterResource(id = if (isFavoriteSelected) R.drawable.ic_favorite_black else R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.content_desc_favorite_icon),
                tint = Color.Black
            )
        }
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray)),
            modifier = Modifier
                .padding(start = 16.dp)
                .height(60.dp)
                .fillMaxWidth(),
            onClick = {
                openDialog = true
            }
        ) {
            Text(
                text = stringResource(id = R.string.btn_add_to_cart),
                style = GelatioTypography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ImageSlider(imageUrl: String, modifier: Modifier = Modifier) {
    Box(modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .fallback(R.drawable.img_minimal_stand)
                .placeholder(R.drawable.img_minimal_stand)
                .build(),
            contentDescription = stringResource(R.string.content_desc_product_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth()
                .aspectRatio(0.95f)
                .padding(start = 52.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp))
        )
        ColorPalette(
            modifier = Modifier.padding(top = 104.dp, start = 20.dp),
            colors = listOf(
                colorResource(id = R.color.color_palette_first),
                colorResource(id = R.color.color_palette_second),
                colorResource(id = R.color.color_palette_third)
            )
        )
    }
}

@Composable
private fun ProductContent(
    product: ProductDetail,
    itemCount: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White)
    ) {
        Text(
            text = product.title,
            style = GelatioTypography.titleMedium,
            color = colorResource(id = R.color.medium_gray),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.product_price_title, product.price),
                style = NunitoSansTypography.bodyLarge,
                color = colorResource(id = R.color.medium_gray),
                modifier = Modifier.weight(1f),
            )
            ShoppingCounter(
                value = itemCount,
                onIncrementClick = onIncrementClick,
                onDecrementClick = onDecrementClick
            )
        }

        ReviewItem(
            rating = product.rating,
            numOfReviews = product.reviews
        )
        Text(
            text = product.description,
            style = NunitoSansTypography.labelSmall,
            color = colorResource(id = R.color.gray),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun ReviewItem(rating: Double, numOfReviews: Int, modifier: Modifier = Modifier) {
    val resources = LocalContext.current.resources
    val reviewCountFormattedString = remember(numOfReviews, resources) {
        resources.getQuantityString(
            R.plurals.review_count,
            numOfReviews, numOfReviews
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = stringResource(R.string.content_desc_rating),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.gold)),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = rating.toString(),
            style = NunitoSansTypography.labelLarge,
            color = colorResource(id = R.color.medium_gray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.review_count_title, reviewCountFormattedString),
            style = NunitoSansTypography.labelSmall,
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}