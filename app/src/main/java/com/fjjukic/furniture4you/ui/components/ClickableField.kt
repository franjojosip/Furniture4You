package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R


@Preview
@Composable
fun ClickableFieldPreview() {
    ClickableField(
        title = stringResource(id = R.string.label_sales),
        subtitle = stringResource(id = R.string.label_sales),
        onClick = {}
    )
}

@Composable
fun ClickableField(
    title: String,
    subtitle: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(4.dp),
        color = colorResource(id = R.color.color_white),
        shadowElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.color_dark_gray)
                    )
                )
                if (subtitle != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = subtitle,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = nunitoSansFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.color_light_gray)
                        )
                    )
                }
            }
            IconButton(onClick = onClick) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_next),
                    tint = colorResource(id = R.color.color_dark_gray),
                    contentDescription = stringResource(R.string.content_desc_action_start_icon)
                )
            }
        }
    }
}