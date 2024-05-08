package com.fjjukic.furniture4you.ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun PaymentMethodPreview() {
    PaymentMethod(PaymentMethodViewModel(), {}, {})
}

@Composable
fun PaymentMethod(
    viewModel: PaymentMethodViewModel,
    onBackClick: () -> Unit,
    onCardAddClick: () -> Unit
) {

    val cards by viewModel.cards.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_payment_method),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(52.dp),
                containerColor = Color.White,
                shape = CircleShape,
                onClick = onCardAddClick,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "icon")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 40.dp, horizontal = 20.dp)
        ) {
            items(cards) {
                PaymentCardItem(
                    modifier = Modifier.padding(bottom = 14.dp)
                )
                DefaultPaymentCheckbox({}, modifier = Modifier.padding(bottom = 30.dp))
            }
        }
    }
}

@Preview
@Composable
fun PaymentCardItemPreview() {
    PaymentCardItem()
}

@Composable
fun PaymentCardItem(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.dark_gray)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Image(
                modifier = Modifier.padding(bottom = 20.dp),
                painter = painterResource(id = R.drawable.ic_mastercard_tinted),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                text = "**** **** **** 3947",
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                maxLines = 1,
                color = colorResource(id = R.color.white),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Card Holder Name",
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.color_card_title),
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        text = "Mark Markinson",
                        fontFamily = nunitoSansFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.white)
                    )
                }

                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 20.dp),
                ) {
                    Text(
                        text = "Expiry Date",
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.color_card_title),
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "06/23",
                        fontFamily = nunitoSansFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.white)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPaymentCheckboxPreview() {
    DefaultPaymentCheckbox({})
}

@Composable
fun DefaultPaymentCheckbox(
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isDefaultChecked: Boolean = false
) {
    val checked by remember {
        mutableStateOf(isDefaultChecked)
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckChange(it)
            },
            modifier = Modifier
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "Use as default payment method",
            fontFamily = nunitoSansFamily,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.color_default_payment_text)
        )
    }
}