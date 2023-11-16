package com.fjjukic.furniture4you.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun CategoryItemSelectedPreview() {
    CategoryItem(
        modifier = Modifier.padding(start = 20.dp),
        title = "Popular",
        isSelected = true
    ) {}
}

@Preview
@Composable
fun CategoryItemUnselectedPreview() {
    CategoryItem(title = "Popular", isSelected = false) {}
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes imageResId: Int = R.drawable.ic_star,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF303030) else Color(0xFFF5F5F5)
    val imageColor = if (isSelected) Color(0xFFFFFFFF) else Color(0xFF909090)

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .width(44.dp)
                .height(44.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onItemSelected.invoke() }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = imageResId),
                contentDescription = stringResource(R.string.content_desc_icon),
                contentScale = ContentScale.None,
                colorFilter = ColorFilter.tint(color = imageColor)
            )
        }
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF242424)
            )
        )
    }
}