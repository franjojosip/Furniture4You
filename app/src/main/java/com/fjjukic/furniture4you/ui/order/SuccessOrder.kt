package com.fjjukic.furniture4you.ui.order

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.common.DisableBackNavigation
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun SuccessOrderPreview() {
    SuccessOrder(onTrackOrdersClicked = {}, onBackToHomeClicked = {})
}

@Composable
fun SuccessOrder(
    onTrackOrdersClicked: () -> Unit,
    onBackToHomeClicked: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackPressedDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    DisableBackNavigation(onBackPressedDispatcher, lifecycleOwner)

    val checkmarkSize = 50.dp
    val offset = checkmarkSize / 2
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 80.dp),
            text = stringResource(R.string.order_success_title).uppercase(),
            style = GelatioTypography.titleLarge,
            color = colorResource(id = R.color.medium_gray),
        )
        Box(
            modifier = Modifier.padding(top = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_success_bg),
                contentDescription = stringResource(R.string.content_desc_product),
                contentScale = ContentScale.Crop,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_success_image),
                contentDescription = stringResource(R.string.content_desc_product),
                contentScale = ContentScale.Crop,
            )
            Image(
                modifier = Modifier
                    .size(checkmarkSize)
                    .align(Alignment.BottomCenter)
                    .offset(y = offset),
                painter = painterResource(id = R.drawable.ic_checkmark_fill),
                contentDescription = stringResource(R.string.content_desc_product)
            )
        }
        Text(
            modifier = Modifier
                .padding(top = offset + 24.dp)
                .padding(horizontal = 45.dp),
            text = stringResource(R.string.order_success_subtitle),
            textAlign = TextAlign.Center,
            fontFamily = nunitoSansFamily,
            fontSize = 18.sp,
            color = colorResource(id = R.color.gray),
        )
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray)),
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(horizontal = 20.dp)
                .height(60.dp)
                .fillMaxWidth(),
            onClick = onTrackOrdersClicked
        ) {
            Text(
                text = stringResource(id = R.string.order_success_btn_track_orders),
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            border = BorderStroke(1.dp, colorResource(id = R.color.medium_gray)),
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 20.dp)
                .height(60.dp)
                .fillMaxWidth(),
            onClick = onBackToHomeClicked
        ) {
            Text(
                text = stringResource(id = R.string.order_success_btn_back_to_home).uppercase(),
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.dark_gray)
            )
        }
    }
}