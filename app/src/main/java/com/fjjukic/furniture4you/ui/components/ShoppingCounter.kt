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

@Preview()
@Composable
fun ShoppingCounterPreview() {
    ShoppingCounter(value = 1, onIncrementClick = {}, onDecrementClick = {})
}

@Composable
fun ShoppingCounter(
    value: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = colorResource(id = R.color.color_dark_gray),
    startIconTint: Color = colorResource(id = R.color.color_dark_gray),
    startIconBackground: Color = colorResource(id = R.color.color_tinted_white),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShoppingCounterItem(
            resourceId = R.drawable.ic_plus,
            onItemClick = { onIncrementClick() },
            startIconTint = startIconTint,
            startIconBackground = startIconBackground
        )
        Text(
            text = stringResource(id = R.string.label_counter, value),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ShoppingCounterItem(
            resourceId = R.drawable.ic_minus,
            onItemClick = { onDecrementClick() },
            startIconTint = startIconTint,
            startIconBackground = startIconBackground
        )
    }
}

@Composable
fun ShoppingCounterItem(
    resourceId: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    startIconTint: Color = colorResource(id = R.color.color_dark_gray),
    startIconBackground: Color = colorResource(id = R.color.color_tinted_white)
) {
    IconButton(
        onClick = {
            onItemClick()
        },
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .size(30.dp)
            .background(startIconBackground)
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            tint = startIconTint,
            contentDescription = null
        )
    }
}