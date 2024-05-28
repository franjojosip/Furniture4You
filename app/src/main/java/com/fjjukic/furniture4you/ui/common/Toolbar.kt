package com.fjjukic.furniture4you.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import ht.ferit.fjjukic.foodlovers.R


@Preview(showBackground = true)
@Composable
fun ToolbarPreview() {
    Toolbar(
        title = stringResource(id = R.string.nav_my_cart),
        startIconResId = R.drawable.ic_search,
        endIconResId = R.drawable.ic_favorite,
        onStartActionClick = {},
        onEndActionClick = {}
    )
}

@Composable
fun Toolbar(
    title: String,
    modifier: Modifier = Modifier,
    startIconResId: Int,
    endIconResId: Int? = null,
    onStartActionClick: () -> Unit,
    onEndActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        IconButton(onClick = onStartActionClick) {
            Icon(
                painter = painterResource(id = startIconResId),
                tint = colorResource(id = R.color.color_dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_start_icon)
            )
        }
        Text(
            text = title,
            style = NunitoSansTypography.titleSmall,
            color = colorResource(id = R.color.color_medium_gray),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        if (onEndActionClick != null && endIconResId != null) {
            IconButton(onClick = onEndActionClick) {
                Icon(
                    painter = painterResource(id = endIconResId),
                    tint = colorResource(id = R.color.color_dark_gray),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
        } else Box(modifier = Modifier.minimumInteractiveComponentSize())
    }
}
