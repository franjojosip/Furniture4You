package com.fjjukic.furniture4you.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.CustomTextField
import com.fjjukic.furniture4you.ui.common.PaymentUtils
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun CartPreview() {
    Cart(CartViewModel(), {}, {}, {})
}

@Composable
fun Cart(
    viewModel: CartViewModel,
    onProductClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
    onCheckoutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val products by viewModel.products.collectAsStateWithLifecycle()
    val price by viewModel.price.collectAsStateWithLifecycle()
    val showMessage by viewModel.showMessage.collectAsState()

    LaunchedEffect(showMessage) {
        val snackbarResId = showMessage?.snackbarResId
        val snackbarActionResId = showMessage?.snackbarActionResId

        if (snackbarResId != null && snackbarActionResId != null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(snackbarResId),
                    context.getString(snackbarActionResId)
                )
                viewModel.onSnackbarShown()
            }
        }
    }

    Scaffold(
        modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.systemBarsPadding(),
                snackbar = { snackbarData -> Snackbar(snackbarData) }
            )
        },
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_my_cart),
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
                PromoCodeField(
                    onPromoCodeEntered = { code ->
                        viewModel.onPromoCodeEntered(code)
                    },
                    Modifier.padding(horizontal = 20.dp)
                )
                TotalPriceItem(
                    price.fullPrice,
                    price.discount,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 18.dp,
                        bottom = 12.dp
                    )
                )
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray)),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = onCheckoutClicked
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_checkout),
                        style = GelatioTypography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        CartScreenContent(
            onProductClicked = onProductClicked,
            onRemoveClicked = viewModel::onRemoveClicked,
            onCounterIncrement = viewModel::onCounterIncrement,
            onCounterDecrement = viewModel::onCounterDecrement,
            products = products,
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
                        PaymentUtils.formatPrice(price)
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
                        PaymentUtils.formatPrice(price * discount)
                    ),
                    color = colorResource(id = R.color.color_discount_price),
                    fontWeight = FontWeight.Medium,
                    style = NunitoSansTypography.bodyMedium
                )
            }
        }
        Row {
            Text(
                text = stringResource(id = R.string.total_label),
                color = colorResource(id = R.color.color_discount_price),
                style = NunitoSansTypography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(
                    id = R.string.product_price_title,
                    PaymentUtils.formatPrice(price - (price * discount))
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
        elevation = CardDefaults.elevatedCardElevation(3.dp)
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
fun CartScreenContent(
    onProductClicked: (String) -> Unit,
    onRemoveClicked: (String) -> Unit,
    onCounterIncrement: (String) -> Unit,
    onCounterDecrement: (String) -> Unit,
    products: List<CartProduct>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 16.dp)
    ) {
        itemsIndexed(products) { index, item ->
            key(item.product.id) {
                CartProductItem(
                    item.product.id,
                    item.count,
                    onProductClicked = {
                        onProductClicked(item.product.id)
                    },
                    onRemoveClicked = {
                        onRemoveClicked(item.product.id)
                    },
                    onCounterIncrement = onCounterIncrement,
                    onCounterDecrement = onCounterDecrement,
                    item.product.imageResId,
                    item.product.price
                )
                if (index != products.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = colorResource(id = R.color.tinted_white),
                        thickness = 1.dp
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(
        title = "My cart",
        startIconResId = R.drawable.ic_search,
        endIconResId = R.drawable.ic_favorite,
        onStartActionClick = { /*TODO*/ },
        onEndActionClick = { /*TODO*/ })
}

@Composable
fun Header(
    title: String,
    startIconResId: Int,
    endIconResId: Int?,
    onStartActionClick: () -> Unit,
    onEndActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        IconButton(onClick = onStartActionClick) {
            Icon(
                painter = painterResource(id = startIconResId),
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = stringResource(R.string.content_desc_action_start_icon)
            )
        }
        Text(
            text = title,
            style = NunitoSansTypography.titleSmall,
            color = colorResource(id = R.color.medium_gray),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = onEndActionClick) {
            if (endIconResId != null) {
                Icon(
                    painter = painterResource(id = endIconResId),
                    tint = colorResource(id = R.color.dark_gray),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
        }
    }
}

@Preview
@Composable
fun CartProductItemPreview() {
    CartProductItem(
        "",
        1,
        onProductClicked = {},
        onRemoveClicked = {},
        onCounterIncrement = { _ -> },
        onCounterDecrement = { _ -> },
        R.drawable.img_minimal_stand,
        "24.99"
    )
}

@Composable
fun CartProductItem(
    productId: String,
    counterValue: Int,
    onProductClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
    onCounterIncrement: (String) -> Unit,
    onCounterDecrement: (String) -> Unit,
    imageResId: Int, price: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onProductClicked
            )
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = imageResId),
            contentDescription = stringResource(R.string.content_desc_product),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp)
                .weight(1f)
        ) {
            Text(
                text = "Coffee Table",
                color = colorResource(id = R.color.light_gray),
                style = NunitoSansTypography.labelSmall,
                maxLines = 2
            )
            Text(
                text = stringResource(id = R.string.product_price_title, price),
                color = colorResource(id = R.color.dark_gray),
                style = NunitoSansTypography.titleSmall,
                modifier = Modifier.padding(top = 6.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                ShoppingCounter(
                    value = counterValue,
                    onIncrementClick = { onCounterIncrement(productId) },
                    onDecrementClick = { onCounterDecrement(productId) },
                    counterTextColor = colorResource(id = R.color.dark_gray),
                    counterIconTint = colorResource(id = R.color.light_gray),
                    counterIconBackground = colorResource(id = R.color.tinted_white)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onRemoveClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
        }
    }
}