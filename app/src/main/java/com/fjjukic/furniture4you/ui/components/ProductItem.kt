package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Preview(
    showBackground = true
)
@Composable
fun ProductItemPreview(modifier: Modifier = Modifier) {
    ProductItem(
        id = "",
        title = "Black Simple Lamp",
        price = "12.00",
        imageResId = R.drawable.black_simple_lamp
    ) {}
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    id: String,
    title: String,
    price: String,
    imageResId: Int,
    onProductClicked: (String) -> Unit
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(150.dp)
                .height(200.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onProductClicked.invoke(id) }
        ) {
            Image(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                painter = painterResource(id = imageResId),
                contentDescription = stringResource(R.string.content_desc_product),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF606060)
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start),
            text = "$ $price",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030)
            )
        )
    }
}