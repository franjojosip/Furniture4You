package com.fjjukic.furniture4you.ui.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.common.CustomTextField
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun CheckoutPreview() {
    Checkout(CheckoutViewModel(), {}, {})
}

@Composable
fun Checkout(
    viewModel: CheckoutViewModel,
    onBackClicked: () -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        modifier,
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_checkout),
                startIconResId = R.drawable.ic_back,
                endIconResId = null,
                onStartActionClick = onBackClicked,
                onEndActionClick = {},
                modifier = Modifier.background(Color.White)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray)),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.new_feature_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        onSubmitClicked()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_submit_order).uppercase(),
                        style = GelatioTypography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        CheckoutScreenContent(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TotalPriceItemPreview(modifier: Modifier = Modifier) {
    TotalPriceItem(price = 130.00, 0.10)
}

@Composable
fun TotalPriceItem(price: Double, discount: Double, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        if (discount != 0.00) {
            Row {
                Text(
                    text = "Price:",
                    color = colorResource(id = R.color.color_discount_price),
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(
                        id = R.string.product_price_title,
                        String.format("%.2f", price)
                    ),
                    color = colorResource(id = R.color.color_discount_price),
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.LineThrough,
                    style = NunitoSansTypography.bodyMedium,
                )
            }
            Row {
                Text(
                    text = "Discount(${(discount * 100).toInt()}%):",
                    color = colorResource(id = R.color.color_discount_price),
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(
                        id = R.string.product_discount_price_title,
                        String.format("%.2f", price * discount)
                    ),
                    color = colorResource(id = R.color.color_discount_price),
                    fontWeight = FontWeight.Medium,
                    style = NunitoSansTypography.bodyMedium
                )
            }
        }
        Row {
            Text(
                text = "Total:",
                color = colorResource(id = R.color.color_discount_price),
                style = NunitoSansTypography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(
                    id = R.string.product_price_title,
                    String.format("%.2f", price - (price * discount))
                ),
                color = colorResource(id = R.color.medium_gray),
                style = NunitoSansTypography.bodyMedium
            )
        }
    }
}

@Composable
fun PromoCodeField(
    onPromoCodeEntered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var promoCode by remember { mutableStateOf("") }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Color.White),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            CustomTextField(
                fieldValue = promoCode,
                onTextChanged = { newText ->
                    promoCode = newText
                },
                placeholder = stringResource(id = R.string.enter_promo_code),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    if (promoCode.isNotBlank()) {
                        onPromoCodeEntered(promoCode)
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(44.dp)
                    .background(colorResource(id = R.color.medium_gray))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.content_desc_enter_promo)
                )
            }
        }
    }
}

@Composable
fun CheckoutScreenContent(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 16.dp)
    ) {
    }
}