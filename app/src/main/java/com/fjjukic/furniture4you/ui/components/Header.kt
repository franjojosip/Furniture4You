package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
fun HeaderPreview() {
    Header(
        title = stringResource(R.string.mock_title_header),
        subtitle = stringResource(R.string.mock_subtitle_header)
    )
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            HorizontalDivider(
                color = colorResource(id = R.color.color_light_gray),
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 30.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = stringResource(R.string.content_desc_launcher_icon),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(64.dp)
            )
            HorizontalDivider(
                color = colorResource(id = R.color.color_light_gray),
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(end = 30.dp)
            )
        }

        if (title != null) {
            Text(
                text = title,
                fontSize = 30.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = colorResource(id = R.color.color_gray),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp)
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle.uppercase(),
                fontSize = 24.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.color_medium_gray),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp)
            )
        }

    }
}