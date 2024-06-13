package com.fjjukic.furniture4you.ui.common.prelogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.theme.gelatioFamily


@Preview
@Composable
fun PreloginScreenPreview() {
    PreloginScreen(onContinueClick = {})
}

@Composable
fun PreloginScreen(
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PreloginViewModel = hiltViewModel()
) {
    val preloginShown = viewModel.preloginShown.collectAsState().value

    LaunchedEffect(preloginShown) {
        if (preloginShown == true) {
            onContinueClick()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_prelogin),
            contentDescription = stringResource(R.string.content_desc_prelogin_background),
            contentScale = ContentScale.FillBounds,
            modifier = modifier.matchParentSize()
        )
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.title_prelogin).uppercase(),
                fontSize = 24.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = colorResource(id = R.color.color_gray),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 30.dp)
                    .padding(horizontal = 24.dp)
            )
            Text(
                text = stringResource(R.string.subtitle_prelogin).uppercase(),
                fontSize = 30.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.color_medium_gray),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp)
                    .padding(horizontal = 24.dp)
            )
            Text(
                text = stringResource(R.string.label_prelogin_description),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                color = colorResource(id = R.color.color_light_gray),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 32.dp, start = 60.dp, end = 24.dp),
                lineHeight = 32.sp
            )
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                modifier = modifier
                    .padding(top = 160.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.onPreloginShown()
                }
            ) {
                Text(
                    text = stringResource(R.string.btn_get_started),
                    fontSize = 18.sp,
                    fontFamily = gelatioFamily,
                    color = colorResource(id = R.color.color_white),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}