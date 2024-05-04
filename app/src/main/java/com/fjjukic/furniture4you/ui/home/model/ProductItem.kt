package com.fjjukic.furniture4you.ui.home.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.components.CartItem
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview(
    showBackground = true
)
@Composable
fun ProductItemPreview() {
    ProductItem(MockRepository.getProducts().first(), {})
}

@Composable
fun ProductItem(
    product: Product,
    onProductClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(150.dp)
                .height(200.dp)
                .clickable { onProductClicked.invoke(product.id) }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = painterResource(id = product.imageResId),
                contentDescription = stringResource(R.string.content_desc_product),
                contentScale = ContentScale.Crop,
            )

            CartItem(
                {
                    onProductClicked.invoke(product.id)
                },
                modifier = Modifier
                    .wrapContentSize()
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 8.dp, start = 2.dp, end = 2.dp),
            text = product.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.gray)
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 2.dp),
            text = stringResource(id = R.string.product_price_title, product.price),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.medium_gray)
            )
        )
    }
}