package com.fjjukic.furniture4you.ui.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fjjukic.furniture4you.ui.components.ColorPalette
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ProductPreviewScreenPreview() {
    ProductDetail(viewModel = ProductDetailViewModel(), {})
}

@Composable
fun ProductDetail(
    viewModel: ProductDetailViewModel,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val productState by viewModel.productState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        topBar = {
            BackButton {
                onBackClicked.invoke()
            }
        },
        bottomBar = {
            ProductBottomButtons()
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
                viewModel::onDecrementClick
            )
        }
    }
}

@Composable
private fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
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
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ProductBottomButtons(modifier: Modifier = Modifier) {
    Row(modifier.padding(16.dp)) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F0F0))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                tint = Color.Black,
                contentDescription = ""
            )
        }
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
            modifier = Modifier
                .padding(start = 16.dp)
                .height(60.dp)
                .fillMaxWidth(),
            onClick = {}
        ) {
            Text(
                text = "Add to cart",
                fontSize = 20.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFFFFFF)
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
                .placeholder(R.drawable.minimal_stand)
                .build(),
            contentDescription = stringResource(R.string.content_desc_launcher_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth()
                .padding(start = 52.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp))
        )
        ColorPalette(
            modifier = Modifier.padding(top = 105.dp, start = 20.dp),
            colors = listOf(Color.White, Color(0xFFB4916C), Color(0xFFE4CBAD))
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
            .padding(24.dp)
            .background(Color.White)
    ) {
        Text(
            text = product.title,
            fontSize = 24.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF303030),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.product_price_title, product.price),
                fontSize = 30.sp,
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030),
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
            fontSize = 14.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.Light,
            color = Color(0xFF606060),
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
            contentDescription = stringResource(R.string.content_desc_icon),
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(Color(0xFFF2C94C))
        )
        Text(
            text = rating.toString(),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF303030),
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.review_count_title, reviewCountFormattedString),
            fontSize = 14.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF808080),
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}