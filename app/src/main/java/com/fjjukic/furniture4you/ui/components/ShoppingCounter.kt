package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ShoppingCounterPreview() {
    ShoppingCounter()
}

@Preview
@Composable
fun ShoppingCounterItemPreview() {
    ShoppingCounterItem() {}
}

@Composable
fun ShoppingCounter(
    modifier: Modifier = Modifier,
    value: Int = 1,
    onIncrementClick: (() -> Unit) = {},
    onDecrementClick: (() -> Unit) = {},
) {
    Row(
        modifier = modifier.padding(16.dp)
    ) {
        ShoppingCounterItem(R.drawable.ic_plus) {
            onIncrementClick.invoke()
        }
        Text(
            text = String.format("%02d", value),
            fontSize = 18.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF242424),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ShoppingCounterItem(R.drawable.ic_minus) {
            onDecrementClick.invoke()
        }
    }
}

@Composable
fun ShoppingCounterItem(
    resourceId: Int = R.drawable.ic_plus,
    onItemClicked: (() -> Unit) = {}
) {
    IconButton(
        onClick = {
            onItemClicked.invoke()
        },
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .size(30.dp)
            .background(Color(0xFFF0F0F0))
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            tint = Color.Black,
            contentDescription = ""
        )
    }
}