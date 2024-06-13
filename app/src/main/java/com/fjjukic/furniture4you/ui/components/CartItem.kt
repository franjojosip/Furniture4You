package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.R


@Preview
@Composable
fun CartItemPreview() {
    CartItem(onItemSelect = {})
}

@Composable
fun CartItem(
    onItemSelect: () -> Unit,
    modifier: Modifier = Modifier,
    itemColor: Color = colorResource(id = R.color.color_white),
    itemBackground: Color = colorResource(id = R.color.color_transparent_gray),
    itemRadius: Dp = 6.dp
) {

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(itemBackground, RoundedCornerShape(itemRadius))
                .padding(6.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onItemSelect() }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                colorFilter = ColorFilter.tint(itemColor),
                contentDescription = stringResource(id = R.string.content_desc_cart),
                contentScale = ContentScale.None
            )
        }
    }
}