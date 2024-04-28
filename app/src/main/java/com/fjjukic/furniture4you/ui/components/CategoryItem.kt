package com.fjjukic.furniture4you.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun CategoryFilterItemSelectedPreview() {
    CategoryFilterItem(title = "Popular", isSelected = true, imageResId = R.drawable.ic_star, {})
}

@Preview
@Composable
fun CategoryFilterItemUnselectedPreview() {
    CategoryFilterItem(title = "Popular", isSelected = false, imageResId = R.drawable.ic_star, {})
}

@Composable
fun CategoryFilterItem(
    title: String,
    isSelected: Boolean,
    @DrawableRes imageResId: Int,
    onCategorySelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) colorResource(id = R.color.color_category_filter_bg_selected)
        else colorResource(id = R.color.color_category_filter_bg_unselected)

    val imageColor =
        if (isSelected) colorResource(id = R.color.color_category_filter_img_selected)
        else colorResource(id = R.color.color_category_filter_img_unselected)

    val textColor =
        if (isSelected) colorResource(id = R.color.color_category_filter_txt_selected)
        else colorResource(id = R.color.color_category_filter_txt_unselected)

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .size(44.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onCategorySelected() }
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = imageResId),
                contentDescription = stringResource(R.string.content_desc_rating),
                contentScale = ContentScale.None,
                colorFilter = ColorFilter.tint(color = imageColor)
            )
        }
        Text(
            text = title,
            style = NunitoSansTypography.labelSmall,
            color = textColor
        )
    }
}