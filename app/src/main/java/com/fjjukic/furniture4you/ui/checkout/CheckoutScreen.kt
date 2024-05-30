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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.dialog.DeliveryChoiceMenuDialog
import com.fjjukic.furniture4you.ui.dialog.PaymentInfoDialog
import com.fjjukic.furniture4you.ui.dialog.ShippingInfoDialog
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun CheckoutPreview() {
    CheckoutScreen(onBackClick = {}, onSubmitClick = {})
}

@Composable
fun CheckoutScreen(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier,
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_checkout),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = { onSubmitClick() }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_submit_order).uppercase(),
                        style = GelatioTypography.bodyMedium,
                        color = colorResource(id = R.color.color_white)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ShippingAddress(
                shippingInfo = uiState.shippingInfo,
                onShippingInfoChange = viewModel::onShippingInfoChange,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
            Payment(
                paymentInfo = uiState.paymentInfo,
                onEditClick = viewModel::onPaymentInfoChange,
                modifier = Modifier.padding(top = 30.dp)
            )
            DeliveryMethod(
                selectedOption = uiState.selectedDelivery,
                deliveryOptions = uiState.deliveryOptions,
                onDeliveryOptionSelect = viewModel::onDeliveryOptionSelect,
                modifier = Modifier.padding(top = 30.dp)
            )
            PriceOverview(uiState.priceInfo)
        }
    }
}


@Preview
@Composable
fun ShippingAddressPreview() {
    ShippingAddress(shippingInfo = MockRepository.getShippingInfo(), onShippingInfoChange = {})
}

@Composable
fun ShippingAddress(
    shippingInfo: ShippingInfo,
    onShippingInfoChange: (ShippingInfo) -> Unit,
    modifier: Modifier = Modifier,
    hasHeader: Boolean = true
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        ShippingInfoDialog(
            shippingInfo,
            onContinueClicked = {
                openDialog = false
                onShippingInfoChange(it)
            },
            onDismissClicked = { openDialog = false }
        )
    }
    Column(modifier = modifier) {
        if (hasHeader) {
            CheckoutItemHeader(
                label = stringResource(R.string.field_shipping_address),
                onEditClick = {
                    openDialog = true
                },
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.color_white)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 16.dp, bottom = 10.dp)
                        .weight(1f),
                    text = shippingInfo.fullName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_medium_gray)
                    )
                )
                if (!hasHeader) {
                    IconButton(
                        onClick = {
                            openDialog = true
                        },
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            tint = colorResource(id = R.color.color_medium_gray),
                            contentDescription = stringResource(id = R.string.content_desc_edit)
                        )
                    }
                }
            }
            HorizontalDivider(
                color = colorResource(id = R.color.color_tinted_white),
                thickness = 2.dp
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
                text = shippingInfo.address,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = nunitoSansFamily,
                    color = colorResource(id = R.color.color_light_gray)
                )
            )
        }
    }
}

@Preview
@Composable
fun CheckoutItemHeaderPreview() {
    CheckoutItemHeader(
        label = stringResource(id = R.string.field_shipping_address),
        onEditClick = {})
}

@Composable
fun CheckoutItemHeader(
    label: String,
    modifier: Modifier = Modifier,
    onEditClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.color_field_title)
            )
        )
        if (onEditClick != null) {
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    tint = colorResource(id = R.color.color_medium_gray),
                    contentDescription = stringResource(id = R.string.content_desc_edit)
                )
            }
        }
    }
}


@Preview
@Composable
fun PaymentPreview() {
    Payment(paymentInfo = MockRepository.getPaymentInfo(), onEditClick = {})
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
            onContinueClick = {
                openDialog = false
                onEditClick(it)
            },
            onDismissClick = {
                openDialog = false
            }
        )
    }
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        CheckoutItemHeader(
            label = stringResource(R.string.title_payment),
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
                containerColor = colorResource(id = R.color.color_white)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(start = 20.dp),
                    shadowElevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = colorResource(id = R.color.color_white),
                ) {
                    Image(
                        modifier = Modifier
                            .background(colorResource(id = R.color.color_white))
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        painter = painterResource(id = R.drawable.ic_mastercard),
                        contentDescription = stringResource(id = R.string.content_desc_vendor)
                    )
                }
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = PaymentUtils.maskCardNumber(paymentInfo.cardNumber),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_medium_gray)
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
    DeliveryMethod(
        selectedOption = options.first(),
        deliveryOptions = options,
        onDeliveryOptionSelect = {})
}

@Composable
fun DeliveryMethod(
    selectedOption: DeliveryOption,
    deliveryOptions: List<DeliveryOption>,
    onDeliveryOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DeliveryChoiceMenuDialog(
            selectedOption.id,
            deliveryOptions,
            onContinueClick = {
                openDialog = false
                onDeliveryOptionSelect(it)
            },
            onDismissClick = {
                openDialog = false
            }
        )
    }
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        CheckoutItemHeader(
            label = stringResource(R.string.title_delivery_method),
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
                containerColor = colorResource(id = R.color.color_white)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .background(colorResource(id = R.color.color_white))
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    painter = painterResource(id = selectedOption.iconResId),
                    contentDescription = stringResource(id = R.string.content_desc_delivery_icon)
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = selectedOption.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_medium_gray)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PriceListPreview() {
    PriceOverview(priceInfo = MockRepository.getPriceInfo())
}

@Composable
fun PriceOverview(
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
                containerColor = colorResource(id = R.color.color_white)
            )
        ) {
            PriceItem(
                titleResId = R.string.title_checkout_order,
                price = priceInfo.orderPrice,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            )
            PriceItem(
                titleResId = R.string.title_checkout_delivery,
                price = priceInfo.deliveryPrice,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            )
            PriceItem(
                titleResId = R.string.title_checkout_total,
                price = priceInfo.orderPrice + priceInfo.deliveryPrice,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                isMainPrice = true
            )
        }
    }
}

@Composable
fun PriceItem(
    titleResId: Int,
    price: Double,
    modifier: Modifier = Modifier,
    isMainPrice: Boolean = false
) {
    Row(modifier) {
        Text(
            text = stringResource(id = titleResId),
            color = colorResource(id = R.color.color_light_gray),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(
                id = R.string.title_product_price,
                PaymentUtils.formatPrice(price)
            ),
            color = colorResource(id = R.color.color_dark_gray),
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = if (isMainPrice) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}