package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ColorPalettePreview() {
    ColorPalette()
}

@Preview
@Composable
fun ColorPickerItemPreview() {
    ColorPickerItem() {}
}

@Composable
fun ColorPalette(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color.Red, Color.Yellow, Color.Green)
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
                ColorPickerItem(isSelected, fillColor) {
                    if (!isSelected) {
                        selectedIndex = index
                    }
                }
                if (index != colors.size - 1) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                }
            }
        }
    }
}

@Composable
fun ColorPickerItem(
    isSelected: Boolean = false,
    fillColor: Color = Color.Magenta,
    onItemClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val borderColor = if (isSelected) Color(0xFF909090) else Color(0XFFF0F0F0)
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .border(
                width = 5.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onItemClicked.invoke()
            }
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(fillColor)
        )
    }
}