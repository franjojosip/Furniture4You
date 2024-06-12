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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.components.ClickableField
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.dialog.LogoutDialog
import com.fjjukic.furniture4you.ui.main.home.BottomBarNavigation
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onLogoutClick = {},
        onNavigateToBottomBarRoute = {},
        onNavigateToRoute = {}
    )
}

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onNavigateToBottomBarRoute: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    var openLogoutDialog by remember { mutableStateOf(false) }

    if (openLogoutDialog) {
        LogoutDialog(
            onConfirmClick = {
                openLogoutDialog = false
                viewModel.onLogoutClick()
            }, onDismissClick = {
                openLogoutDialog = false
            }
        )
    }

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut == true) {
            onLogoutClick()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_profile),
                startIconResId = R.drawable.ic_search,
                endIconResId = R.drawable.ic_logout,
                onStartActionClick = {
                    onNavigateToRoute(Screens.Search.route)
                },
                onEndActionClick = {
                    openLogoutDialog = true
                },
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            BottomBarNavigation(
                currentRoute = Screens.HomeSections.Profile.route,
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
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
                        text = state.personalInformation.name,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.color_medium_gray),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.personalInformation.email,
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
                onClick = {
                    onNavigateToRoute(Screens.ProfileSections.MyOrders.route)
                }
            )
            ClickableField(
                title = stringResource(id = R.string.label_shipping_addresses),
                subtitle = stringResource(R.string.mock_shipping_addresses),
                onClick = {
                    onNavigateToRoute(Screens.ProfileSections.ShippingAddress.route)
                }
            )
            ClickableField(
                title = stringResource(id = R.string.title_payment_method),
                subtitle = stringResource(R.string.mock_payment_method),
                onClick = {
                    onNavigateToRoute(Screens.ProfileSections.PaymentMethod.route)
                }
            )
            ClickableField(
                title = stringResource(id = R.string.nav_my_reviews),
                subtitle = stringResource(R.string.mock_my_reviews),
                onClick = {
                    onNavigateToRoute(Screens.ProfileSections.MyReviews.route)
                }
            )
            ClickableField(
                title = stringResource(id = R.string.nav_settings),
                subtitle = stringResource(R.string.label_settings),
                onClick = {
                    onNavigateToRoute(Screens.ProfileSections.Settings.route)
                }
            )
        }
    }
}