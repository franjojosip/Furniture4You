package com.fjjukic.furniture4you.ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.PaymentCard
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun PaymentMethodScreenPreview() {
    PaymentMethodScreen(onCardAddClick = {}, onBackClick = {})
}

@Composable
fun PaymentMethodScreen(
    onCardAddClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: PaymentMethodViewModel = hiltViewModel(),
) {
    val cards by viewModel.cards.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_payment_method),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(52.dp),
                containerColor = colorResource(id = R.color.color_white),
                contentColor = colorResource(id = R.color.bg_fab_content),
                shape = CircleShape,
                onClick = onCardAddClick,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.label_add_payment_method)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 20.dp)
        ) {
            items(cards) { card ->
                PaymentCardItem(
                    card = card,
                    modifier = Modifier.padding(bottom = 14.dp)
                )
                DefaultPaymentCheckbox(
                    { isChecked ->
                        if (!card.isDefault && isChecked) {
                            viewModel.onCheckboxCheck(card.id)
                        }
                    },
                    modifier = Modifier.padding(bottom = 30.dp),
                    isDefaultChecked = card.isDefault
                )
            }
        }
    }
}

@Preview
@Composable
fun PaymentCardItemPreview() {
    PaymentCardItem(card = MockRepository.getPaymentCards().first())
}

@Composable
fun PaymentCardItem(
    card: PaymentCard,
    modifier: Modifier = Modifier
) {
    val containerAlpha = if (card.isDefault) 1f else 0.5f
    Card(
        modifier = modifier
            .alpha(containerAlpha)
            .defaultMinSize(minHeight = 190.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.color_dark_gray)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .height(25.dp),
                    painter = painterResource(id = card.vendorLogoResId),
                    contentDescription = stringResource(id = R.string.content_desc_vendor)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    text = PaymentUtils.maskCardNumber(card.cardNumber),
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    letterSpacing = 4.sp,
                    maxLines = 1,
                    color = colorResource(id = R.color.color_white),
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.label_card_holder_name),
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
                            text = card.cardHolder,
                            fontFamily = nunitoSansFamily,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = colorResource(id = R.color.color_white)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(end = 20.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.label_expiry_date),
                            fontFamily = nunitoSansFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = colorResource(id = R.color.color_card_title),
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            text = PaymentUtils.formatExpDate(card.expDate),
                            fontFamily = nunitoSansFamily,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = colorResource(id = R.color.color_white)
                        )
                    }
                }
            }
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp),
                painter = painterResource(id = R.drawable.ic_card_overlay),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPaymentCheckboxPreview() {
    DefaultPaymentCheckbox(onCheckChange = {})
}

@Composable
fun DefaultPaymentCheckbox(
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isDefaultChecked: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (!isDefaultChecked) {
                        onCheckChange(true)
                    }
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isDefaultChecked,
            onCheckedChange = {
                if (!isDefaultChecked) {
                    onCheckChange(true)
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.bg_checkbox_checked),
                uncheckedColor = colorResource(id = R.color.bg_checkbox_unchecked)
            )
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = stringResource(R.string.label_default_payment_method),
            fontFamily = nunitoSansFamily,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.color_default_payment_text)
        )
    }
}