package com.fjjukic.furniture4you.ui.common

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R


@Preview
@Composable
fun PreloginScreenPreview() {
    PreloginScreen()
}

@Composable
fun PreloginScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: (() -> Unit)? = null
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.prelogin_background),
            contentDescription = stringResource(R.string.content_desc_prelogin_background),
            contentScale = ContentScale.FillBounds,
            modifier = modifier.matchParentSize()
        )
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.prelogin_title).uppercase(),
                fontSize = 24.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = Color(0xFF606060),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp, end = 24.dp, top = 30.dp)
            )
            Text(
                text = stringResource(R.string.prelogin_subtitle).uppercase(),
                fontSize = 30.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp, start = 24.dp, end = 24.dp)
            )
            Text(
                text = stringResource(R.string.prelogin_description),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                color = Color(0xFF808080),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 32.dp, start = 60.dp, end = 24.dp),
                lineHeight = 32.sp
            )
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
                modifier = modifier
                    .padding(top = 160.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { onContinueClicked?.invoke() }) {
                Text(
                    text = stringResource(R.string.prelogin_button_get_started),
                    fontSize = 18.sp,
                    fontFamily = gelatioFamily,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}