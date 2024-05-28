package com.fjjukic.furniture4you.ui.rating

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.Toolbar
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R
import java.util.Locale

@Preview
@Composable
fun RatingReviewPreview() {
    RatingReview(onBackClick = {}, onSubmitClick = {})
}

@Composable
fun RatingReview(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RatingReviewViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        containerColor = colorResource(id = R.color.color_transparent),
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_rating_review),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.color_transparent)
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.color_transparent)),
                    onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.label_new_feature),
                            Toast.LENGTH_SHORT
                        ).show()
                        onSubmitClick()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_write_review),
                        style = GelatioTypography.bodyMedium,
                        color = colorResource(id = R.color.color_white)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            ProductHeader(
                title = uiState.product.title,
                rating = uiState.product.rating,
                reviews = uiState.product.reviews,
                modifier = Modifier.padding(top = 20.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
                color = colorResource(id = R.color.color_tinted_white),
                thickness = 1.dp
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.color_white)),
                contentPadding = PaddingValues(
                    top = 24.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                items(uiState.reviews) { review ->
                    Review(
                        review = review,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductHeaderPreview() {
    val product = MockRepository.getProductDetail()
    ProductHeader(
        title = product.title,
        rating = product.rating,
        reviews = product.reviews
    )
}

@Composable
fun ProductHeader(
    title: String,
    rating: Double,
    reviews: Int,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val reviewCountFormattedString = remember(reviews, resources) {
        resources.getQuantityString(R.plurals.label_review_count, reviews, reviews)
    }

    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.img_minimal_stand),
            contentDescription = stringResource(R.string.content_desc_product),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontFamily = nunitoSansFamily,
                fontSize = 14.sp,
                color = colorResource(id = R.color.color_medium_gray)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = stringResource(R.string.content_desc_rating),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.color_star)),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = String.format(
                        Locale.getDefault(),
                        "%.1f",
                        rating
                    ),
                    fontFamily = nunitoSansFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.color_medium_gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = reviewCountFormattedString,
                fontFamily = nunitoSansFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.color_medium_gray)
            )
        }
    }
}

@Preview
@Composable
fun ReviewPreview() {
    Review(review = MockRepository.getReviews().first())
}

@Composable
fun Review(review: Review, modifier: Modifier = Modifier) {
    Box {
        Card(
            modifier = modifier
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.color_white)
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = review.name,
                    fontFamily = nunitoSansFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.color_dark_gray),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = review.date,
                    fontFamily = nunitoSansFamily,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.color_light_gray)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 16.dp)
            ) {
                repeat(5) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = stringResource(R.string.content_desc_rating),
                        contentScale = ContentScale.None,
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.color_star)),
                        modifier = Modifier.padding(end = 2.dp)
                    )
                }
            }
            Text(
                text = review.description,
                fontFamily = nunitoSansFamily,
                fontSize = 14.sp,
                color = colorResource(id = R.color.color_dark_gray),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp, bottom = 20.dp)
            )
        }
        Image(
            painter = painterResource(id = review.imageResId),
            contentDescription = stringResource(R.string.content_desc_rating),
            modifier = Modifier
                .size(40.dp)
                .offset(y = (-20).dp)
                .align(Alignment.TopCenter),
        )
    }
}