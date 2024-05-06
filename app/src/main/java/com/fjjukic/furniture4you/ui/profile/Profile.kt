package com.fjjukic.furniture4you.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ProfilePreview() {
    Profile({}, {}, {}, {})
}

@Composable
fun Profile(
    onSearchClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onMyReviewsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_profile),
                startIconResId = R.drawable.ic_search,
                endIconResId = R.drawable.ic_logout,
                onStartActionClick = onSearchClick,
                onEndActionClick = onLogoutClick,
                modifier = Modifier.background(Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 18.dp)
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(100)),
                    painter = painterResource(id = R.drawable.coffee_chair),
                    contentDescription = stringResource(R.string.content_desc_rating),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                        .padding(top = 16.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Bruno Fernandes",
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.medium_gray),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "bruno.fernandes@gmail.com",
                        fontFamily = nunitoSansFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.light_gray)
                    )
                }
            }
            ClickableField(
                title = stringResource(id = R.string.label_my_orders),
                subtitle = "Already have 10 orders",
                onClick = { /*TODO*/ }
            )
            ClickableField(
                title = stringResource(id = R.string.label_shipping_addresses),
                subtitle = "3 Addresses",
                onClick = { /*TODO*/ }
            )
            ClickableField(
                title = stringResource(id = R.string.payment_method),
                subtitle = "You have 2 cards",
                onClick = { /*TODO*/ }
            )
            ClickableField(
                title = stringResource(id = R.string.nav_my_reviews),
                subtitle = "Reviews for 5 items",
                onClick = onMyReviewsClick
            )
            ClickableField(
                title = stringResource(id = R.string.nav_settings),
                subtitle = "Notification, Password, FAQ, Contact us",
                onClick = onSettingsClick
            )
        }
    }
}