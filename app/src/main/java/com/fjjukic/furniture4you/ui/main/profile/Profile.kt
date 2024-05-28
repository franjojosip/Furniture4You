package com.fjjukic.furniture4you.ui.main.profile

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.Toolbar
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ProfilePreview() {
    Profile(
        onSearchClick = {},
        onLogoutClick = {},
        onPaymentMethodClick = {},
        onMyReviewsClick = {},
        onSettingsClick = {},
        onShippingClick = {},
        onMyOrderClick = {})
}

@Composable
fun Profile(
    onSearchClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onPaymentMethodClick: () -> Unit,
    onMyReviewsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onShippingClick: () -> Unit,
    onMyOrderClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_profile),
                startIconResId = R.drawable.ic_search,
                endIconResId = R.drawable.ic_logout,
                onStartActionClick = onSearchClick,
                onEndActionClick = onLogoutClick,
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 18.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(100)),
                    painter = painterResource(id = R.drawable.img_coffee_chair),
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
                        text = uiState.personalInformation.name,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.color_medium_gray),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.personalInformation.email,
                        fontFamily = nunitoSansFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.color_light_gray)
                    )
                }
            }
            ClickableField(
                title = stringResource(id = R.string.label_my_orders),
                subtitle = stringResource(R.string.mock_my_orders),
                onClick = onMyOrderClick
            )
            ClickableField(
                title = stringResource(id = R.string.label_shipping_addresses),
                subtitle = stringResource(R.string.mock_shipping_addresses),
                onClick = onShippingClick
            )
            ClickableField(
                title = stringResource(id = R.string.title_payment_method),
                subtitle = stringResource(R.string.mock_payment_method),
                onClick = onPaymentMethodClick
            )
            ClickableField(
                title = stringResource(id = R.string.nav_my_reviews),
                subtitle = stringResource(R.string.mock_my_reviews),
                onClick = onMyReviewsClick
            )
            ClickableField(
                title = stringResource(id = R.string.nav_settings),
                subtitle = stringResource(R.string.label_settings),
                onClick = onSettingsClick
            )
        }
    }
}