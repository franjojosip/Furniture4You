package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ht.ferit.fjjukic.foodlovers.R


@Preview
@Composable
fun ShoppingItemPreview() {
    ShoppingItem() {}
}

@Composable
fun ShoppingItem(
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit
) {

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color(0x66606060), RoundedCornerShape(12.dp))
                .size(44.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onItemSelected.invoke() }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = "",
                contentScale = ContentScale.None
            )
        }
    }
}