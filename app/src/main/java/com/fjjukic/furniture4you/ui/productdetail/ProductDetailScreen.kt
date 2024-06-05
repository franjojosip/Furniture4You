package com.fjjukic.furniture4you.ui.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.components.ColorPalette
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.dialog.ProductDetailCartDialog
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography

@Preview
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(onBackClick = {}, onNavigateToCartClick = {}, onReviewClick = {})
}

@Composable
fun ProductDetailScreen(
    onNavigateToCartClick: () -> Unit,
    onReviewClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val productState by viewModel.productState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        topBar = {
            BackButton {
                onBackClick()
            }
        },
        bottomBar = {
            ProductBottomButtons(onNavigateToCartClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.color_white))
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            ImageSlider(
                imageUrl = productState.selectedProductDetail.imageUrl,
                options = productState.selectedProductDetail.productOptions
            )
            ProductContent(
                product = productState.selectedProductDetail,
                itemCount = productState.counter,
                onIncrementClick = viewModel::onIncrementClick,
                onDecrementClick = viewModel::onDecrementClick,
                onReviewClick = onReviewClick,
                modifier = Modifier.padding(24.dp)
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
fun ProductBottomButtons(
    onNavigateToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavoriteSelected by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        ProductDetailCartDialog(
            onContinueClick = {
                openDialog = false
            },
            onDismissClick = {
                openDialog = false
                onNavigateToCartClick()
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
                .background(colorResource(id = R.color.color_tinted_white))
        ) {
            Icon(
                painter = painterResource(id = if (isFavoriteSelected) R.drawable.ic_favorite_black else R.drawable.ic_favorite),
                contentDescription = stringResource(id = R.string.content_desc_favorite_icon),
                tint = colorResource(id = R.color.color_black)
            )
        }
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
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
                color = colorResource(id = R.color.color_white)
            )
        }
    }
}

@Composable
private fun ImageSlider(
    imageUrl: String,
    options: List<ProductColorOption>,
    modifier: Modifier = Modifier
) {
    var currentImage by remember { mutableStateOf(imageUrl) }
    Box(modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentImage)
                .crossfade(true)
                .fallback(R.drawable.img_white_table)
                .placeholder(R.drawable.img_white_table)
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
            ),
            onColorSelect = { index ->
                currentImage = options[index].imageUrl
            }
        )
    }
}

@Composable
private fun ProductContent(
    product: ProductDetail,
    itemCount: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    onReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(colorResource(id = R.color.color_white))
    ) {
        Text(
            text = product.title,
            style = GelatioTypography.titleMedium,
            color = colorResource(id = R.color.color_medium_gray),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.title_product_price, product.price),
                style = NunitoSansTypography.bodyLarge,
                color = colorResource(id = R.color.color_medium_gray),
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
            numOfReviews = product.reviews,
            onReviewClick = onReviewClick
        )
        Text(
            text = product.description,
            style = NunitoSansTypography.labelSmall,
            color = colorResource(id = R.color.color_gray),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun ReviewItem(
    rating: Double,
    numOfReviews: Int,
    onReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val reviewCountFormattedString = remember(numOfReviews, resources) {
        resources.getQuantityString(
            R.plurals.label_review_count,
            numOfReviews, numOfReviews
        )
    }

    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onReviewClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = stringResource(R.string.content_desc_rating),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.color_star)),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = rating.toString(),
            style = NunitoSansTypography.labelLarge,
            color = colorResource(id = R.color.color_medium_gray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.title_review_count, reviewCountFormattedString),
            style = NunitoSansTypography.labelSmall,
            color = colorResource(id = R.color.color_light_gray),
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}