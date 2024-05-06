package com.fjjukic.furniture4you.ui.rating

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun MyReviewPreview() {
    MyReview(
        viewModel = MyReviewViewModel(),
        onBackClicked = {},
        onSubmitClicked = {})
}

@Composable
fun MyReview(
    viewModel: MyReviewViewModel,
    onBackClicked: () -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        containerColor = Color.Transparent,
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_my_reviews),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClicked,
                onEndActionClick = {},
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
                        onSubmitClicked()
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
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White),
                contentPadding = PaddingValues(
                    top = 24.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                items(uiState) { review ->
                    ReviewCard(
                        review = review,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ReviewCardPreview() {
    ReviewCard(MockRepository.getMyReviews().first())
}

@Composable
fun ReviewCard(review: MyReviewModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = review.imageResId),
                contentDescription = stringResource(R.string.content_desc_rating),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = review.title,
                    fontFamily = nunitoSansFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.gray)
                )
                Text(
                    text = stringResource(id = R.string.product_price_title, review.price),
                    fontFamily = nunitoSansFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.dark_gray)
                )

            }
        }
        Row(
            modifier = Modifier
                .padding(top = 14.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            repeat(5) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = stringResource(R.string.content_desc_rating),
                    contentScale = ContentScale.None,
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.gold)),
                    modifier = Modifier.padding(end = 2.dp)
                )
            }
            Text(
                text = review.date,
                fontFamily = nunitoSansFamily,
                fontSize = 12.sp,
                color = colorResource(id = R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = review.description,
            fontFamily = nunitoSansFamily,
            fontSize = 14.sp,
            color = colorResource(id = R.color.dark_gray),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp)
        )
    }
}