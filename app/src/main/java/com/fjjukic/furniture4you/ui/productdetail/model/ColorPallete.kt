package com.fjjukic.furniture4you.ui.productdetail.model

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ColorPalettePreview() {
    ColorPalette(onColorSelected = {})
}

@Preview
@Composable
fun ColorPickerItemPreview() {
    ColorPickerItem({})
}

@Composable
fun ColorPalette(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color.Red, Color.Yellow, Color.Green),
    onColorSelected: (Int) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Surface(
        shadowElevation = 8.dp,
        modifier = modifier,
        shape = RoundedCornerShape(36.dp),
        contentColor = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            colors.forEachIndexed { index, fillColor ->
                val isSelected = index == selectedIndex
                ColorPickerItem(
                    onItemClicked = {
                        if (!isSelected) {
                            selectedIndex = index
                            onColorSelected(index)
                        }
                    },
                    isSelected = isSelected,
                    fillColor = fillColor,
                )
                if (index != colors.size - 1) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                }
            }
        }
    }
}

@Composable
fun ColorPickerItem(
    onItemClicked: () -> Unit,
    isSelected: Boolean = false,
    fillColor: Color = Color.Magenta,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val borderColor =
        if (isSelected) colorResource(id = R.color.color_palette_selected) else colorResource(id = R.color.color_palette_unselected)

    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .border(
                width = 4.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onItemClicked
            )
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(fillColor)
        )
    }
}