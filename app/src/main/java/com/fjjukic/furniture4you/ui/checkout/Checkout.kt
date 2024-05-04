package com.fjjukic.furniture4you.ui.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.checkout.dialog.DeliveryChoiceMenuDialog
import com.fjjukic.furniture4you.ui.checkout.dialog.PaymentInfoDialog
import com.fjjukic.furniture4you.ui.checkout.dialog.ShippingInfoDialog
import com.fjjukic.furniture4you.ui.common.PaymentUtils
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ShippingAddress(
                uiState.shippingInfo,
                viewModel::onShippingInfoChanged,
                modifier = Modifier.padding(top = 20.dp)
            )
            Payment(
                uiState.paymentInfo,
                viewModel::onPaymentInfoChanged,
                modifier = Modifier.padding(top = 30.dp)
            )
            DeliveryMethod(
                uiState.selectedDelivery,
                uiState.deliveryOptions,
                onOptionSelected = {
                    viewModel.onDeliveryOptionSelected(it)
                },
                modifier = Modifier.padding(top = 30.dp)
            )
            PriceList(uiState.priceInfo)
        }
    }
}


@Preview
@Composable
fun ShippingAddressPreview() {
    ShippingAddress(MockRepository.getShippingInfo(), {})
}

@Composable
fun ShippingAddress(
    shippingInfo: ShippingInfo,
    onEditClick: (ShippingInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        ShippingInfoDialog(
            shippingInfo,
            onContinueClicked = {
                openDialog = false
                onEditClick(it)
            },
            onDismissClicked = {
                openDialog = false
            }
        )
    }
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        CheckoutItemHeader(
            label = stringResource(R.string.shipping_address),
            onEditClick = {
                openDialog = true
            }
        )
        Card(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 10.dp
                ),
                text = shippingInfo.fullName,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.medium_gray)
                )
            )
            HorizontalDivider(
                color = colorResource(id = R.color.tinted_white),
                thickness = 2.dp
            )
            Text(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 16.dp
                ),
                text = shippingInfo.address,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = nunitoSansFamily,
                    color = colorResource(id = R.color.light_gray)
                )
            )
        }
    }
}

@Preview
@Composable
fun CheckoutItemHeaderPreview() {
    CheckoutItemHeader(stringResource(id = R.string.shipping_address), {})
}

@Composable
fun CheckoutItemHeader(label: String, onEditClick: () -> Unit, modifier: Modifier = Modifier) {
    Row {
        Text(
            modifier = modifier.weight(1f),
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.checkout_field_color)
            )
        )
        IconButton(
            onClick = onEditClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                tint = colorResource(id = R.color.medium_gray),
                contentDescription = stringResource(id = R.string.content_desc_edit)
            )
        }
    }
}


@Preview
@Composable
fun PaymentPreview() {
    Payment(MockRepository.getPaymentInfo(), {})
}

@Composable
fun Payment(
    paymentInfo: PaymentInfo,
    onEditClick: (PaymentInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        PaymentInfoDialog(
            paymentInfo,
            onContinueClicked = {
                openDialog = false
                onEditClick(it)
            },
            onDismissClicked = {
                openDialog = false
            }
        )
    }
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        CheckoutItemHeader(
            label = stringResource(R.string.payment),
            onEditClick = {
                openDialog = true
            }
        )
        Card(
            modifier = Modifier
                .padding(top = 12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 16.dp),
                    shadowElevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = Color.White,
                ) {
                    Image(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        painter = painterResource(id = R.drawable.mastercard),
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = PaymentUtils.maskCardNumber(paymentInfo.cardNumber),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.medium_gray)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun DeliveryMethodPreview() {
    val options = MockRepository.getDeliveryOptions()
    DeliveryMethod(options.first(), options, {})
}

@Composable
fun DeliveryMethod(
    selectedOption: DeliveryOption,
    deliveryOptions: List<DeliveryOption>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DeliveryChoiceMenuDialog(
            selectedOption.id,
            deliveryOptions,
            onContinueClicked = {
                openDialog = false
                onOptionSelected(it)
            },
            onDismissClicked = {
                openDialog = false
            }
        )
    }
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        CheckoutItemHeader(
            label = stringResource(R.string.delivery_method),
            onEditClick = {
                openDialog = true
            }
        )
        Card(
            modifier = Modifier
                .padding(top = 12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    painter = painterResource(id = selectedOption.iconResId),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = selectedOption.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.medium_gray)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PriceListPreview() {
    PriceList(MockRepository.getPriceInfo())
}

@Composable
fun PriceList(
    priceInfo: PriceInfo,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        Card(
            modifier = Modifier.padding(top = 38.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            PriceItem(
                R.string.order_label,
                priceInfo.orderPrice,
                Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
            )
            PriceItem(
                R.string.delivery_label,
                priceInfo.deliveryPrice,
                Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
            )
            PriceItem(
                R.string.total_label,
                priceInfo.orderPrice + priceInfo.deliveryPrice,
                Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                true
            )
        }
    }
}


@Preview
@Composable
fun PriceItemPreview() {
    PriceItem(R.string.order_label, 85.00)
}

@Composable
fun PriceItem(
    titleResId: Int,
    price: Double,
    modifier: Modifier = Modifier,
    isMainPrice: Boolean = false
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = titleResId),
            color = colorResource(id = R.color.light_gray),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(
                id = R.string.product_price_title,
                PaymentUtils.formatPrice(price)
            ),
            color = colorResource(id = R.color.dark_gray),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = if (isMainPrice) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}