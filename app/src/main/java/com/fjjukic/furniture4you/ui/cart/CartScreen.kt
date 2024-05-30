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
import androidx.compose.ui.layout.ContentScale
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.fields.PromoCodeField
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.CartPrice
import com.fjjukic.furniture4you.ui.common.model.CartProduct
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun CartPreview() {
    CartScreen(onProductClick = {}, onBackClick = {}, onCheckoutClick = {})
}

@Composable
fun CartScreen(
    onProductClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
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
            Toolbar(
                title = stringResource(id = R.string.nav_my_cart),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                PromoCodeField(
                    viewModel::onPromoCodeEnter,
                    Modifier.padding(horizontal = 20.dp)
                )
                TotalPriceItem(
                    price.price,
                    price.discount,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 12.dp)
                        .padding(horizontal = 20.dp)
                )
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .padding(horizontal = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = onCheckoutClick
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_checkout),
                        style = GelatioTypography.bodyMedium,
                        color = colorResource(id = R.color.color_white)
                    )
                }
            }
        }
    ) { paddingValues ->
        CartScreenContent(
            products = products,
            onProductClick = onProductClick,
            onRemoveClick = viewModel::onRemoveClick,
            onIncrementClick = viewModel::onIncrementClick,
            onDecrementClick = viewModel::onDecrementClick,
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TotalPriceItemPreview() {
    val cartPrice = CartPrice()
    TotalPriceItem(price = cartPrice.price, discount = cartPrice.discount)
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
                    text = stringResource(R.string.label_cart_price),
                    color = colorResource(id = R.color.color_discount_price),
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(
                        id = R.string.title_product_price,
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
                    text = stringResource(R.string.label_cart_discount, (discount * 100).toInt()),
                    color = colorResource(id = R.color.color_discount_price),
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(
                        id = R.string.title_product_discount,
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
                text = stringResource(id = R.string.title_checkout_total),
                color = colorResource(id = R.color.color_discount_price),
                style = NunitoSansTypography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(
                    id = R.string.title_product_price,
                    PaymentUtils.formatPrice(price - (price * discount))
                ),
                color = colorResource(id = R.color.color_medium_gray),
                style = NunitoSansTypography.bodyMedium
            )
        }
    }
}

@Composable
fun PromoCodeField(
    onPromoCodeEnter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var promoCode by remember { mutableStateOf("") }
    var promoCodeEntered by remember { mutableStateOf(false) }
    val promoFieldIconResId = if (promoCodeEntered) R.drawable.ic_trash else R.drawable.ic_next

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(colorResource(id = R.color.color_white)),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            PromoCodeField(
                fieldValue = promoCode,
                onTextChange = {
                    promoCode = it
                },
                isEnabled = !promoCodeEntered,
                placeholder = stringResource(id = R.string.placeholder_enter_promo_code),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    if (promoCode.isNotBlank() && promoCodeEntered) {
                        promoCode = ""
                        promoCodeEntered = false
                    } else if (promoCode.isNotBlank()) {
                        onPromoCodeEnter(promoCode)
                        promoCodeEntered = true
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(44.dp)
                    .background(colorResource(id = R.color.color_medium_gray))
            ) {
                Icon(
                    painter = painterResource(id = promoFieldIconResId),
                    tint = colorResource(id = R.color.color_white),
                    contentDescription = stringResource(id = R.string.content_desc_enter_promo)
                )
            }
        }
    }
}

@Composable
fun CartScreenContent(
    products: List<CartProduct>,
    onProductClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit,
    onIncrementClick: (String) -> Unit,
    onDecrementClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
    ) {
        itemsIndexed(products) { index, item ->
            key(item.product.id) {
                CartProductItem(
                    item,
                    onProductClick = onProductClick,
                    onRemoveClick = onRemoveClick,
                    onIncrementClick = onIncrementClick,
                    onDecrementClick = onDecrementClick
                )
                if (index != products.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = colorResource(id = R.color.color_tinted_white),
                        thickness = 1.dp
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun CartProductItemPreview() {
    CartProductItem(
        cartProduct = MockRepository.getCartProduct(),
        onProductClick = {},
        onRemoveClick = {},
        onIncrementClick = {},
        onDecrementClick = {}
    )
}

@Composable
fun CartProductItem(
    cartProduct: CartProduct,
    onProductClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit,
    onIncrementClick: (String) -> Unit,
    onDecrementClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onProductClick(cartProduct.product.id)
                }
            )
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = cartProduct.product.imageResId),
            contentDescription = stringResource(R.string.content_desc_product),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp)
                .weight(1f)
        ) {
            Text(
                text = cartProduct.product.title,
                color = colorResource(id = R.color.color_light_gray),
                style = NunitoSansTypography.labelSmall,
                maxLines = 2
            )
            Text(
                text = stringResource(id = R.string.title_product_price, cartProduct.product.price),
                color = colorResource(id = R.color.color_dark_gray),
                style = NunitoSansTypography.titleSmall,
                modifier = Modifier.padding(top = 6.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                ShoppingCounter(
                    value = cartProduct.count,
                    onIncrementClick = { onIncrementClick(cartProduct.product.id) },
                    onDecrementClick = { onDecrementClick(cartProduct.product.id) },
                    textColor = colorResource(id = R.color.color_dark_gray),
                    startIconTint = colorResource(id = R.color.color_light_gray),
                    startIconBackground = colorResource(id = R.color.color_tinted_white)
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
                onClick = { onRemoveClick(cartProduct.product.id) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.content_desc_action_end_icon)
                )
            }
        }
    }
}