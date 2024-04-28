package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ShoppingCounterPreview() {
    ShoppingCounter(1, {}, {})
}

@Preview
@Composable
fun ShoppingCounterItemPreview() {
    ShoppingCounterItem {}
}

@Composable
fun ShoppingCounter(
    value: Int,
    onIncrementClick: (() -> Unit),
    onDecrementClick: (() -> Unit),
    modifier: Modifier = Modifier,
    counterTextColor: Color = colorResource(id = R.color.dark_gray),
    counterIconTint: Color = colorResource(id = R.color.dark_gray),
    counterIconBackground: Color = colorResource(id = R.color.tinted_white),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShoppingCounterItem(R.drawable.ic_plus, counterIconTint, counterIconBackground) {
            onIncrementClick.invoke()
        }
        Text(
            text = stringResource(id = R.string.counter_label, value),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.SemiBold,
            color = counterTextColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ShoppingCounterItem(R.drawable.ic_minus, counterIconTint, counterIconBackground) {
            onDecrementClick.invoke()
        }
    }
}

@Composable
fun ShoppingCounterItem(
    resourceId: Int = R.drawable.ic_plus,
    startIconTint: Color = colorResource(id = R.color.dark_gray),
    startIconBackground: Color = colorResource(id = R.color.tinted_white),
    onItemClicked: (() -> Unit) = {}
) {
    IconButton(
        onClick = {
            onItemClicked.invoke()
        },
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .size(30.dp)
            .background(startIconBackground)
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            tint = startIconTint,
            contentDescription = ""
        )
    }
}