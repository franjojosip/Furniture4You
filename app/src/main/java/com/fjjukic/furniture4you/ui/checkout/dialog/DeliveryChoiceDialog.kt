package com.fjjukic.furniture4you.ui.checkout.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fjjukic.furniture4you.ui.checkout.DeliveryOption
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R


@Composable
fun DeliveryChoiceMenuDialog(
    selectedOptionId: String,
    deliveryOptions: List<DeliveryOption>,
    onContinueClick: (String) -> Unit,
    onDismissClick: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true
) {
    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        DeliveryChoiceMenu(
            selectedOptionId = selectedOptionId,
            deliveryOptions = deliveryOptions,
            onDismissClick = onDismissClick,
            onContinueClick = onContinueClick
        )
    }
}

@Composable
fun DeliveryChoiceMenu(
    selectedOptionId: String,
    deliveryOptions: List<DeliveryOption>,
    onDismissClick: () -> Unit,
    onContinueClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember {
        mutableIntStateOf(deliveryOptions.indexOfFirst { it.id == selectedOptionId })
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier.background(colorResource(id = R.color.white))
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 12.dp),
                text = stringResource(R.string.choose_delivery_method),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.medium_gray),
            )
            deliveryOptions.forEachIndexed { index, option ->
                ChoiceItem(
                    option.description,
                    option.price,
                    isSelected = index == selectedIndex
                ) {
                    selectedIndex = index
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDismissClick) {
                    Text(
                        stringResource(id = R.string.delivery_choice_btn_cancel),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    onContinueClick(deliveryOptions[selectedIndex].id)
                }) {
                    Text(
                        stringResource(id = R.string.delivery_choice_btn_confirm),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun DeliveryChoicePreview() {
    DeliveryChoiceMenu("", MockRepository.getDeliveryOptions(), {}, {})
}

@Composable
fun ChoiceItem(label: String, price: Double, isSelected: Boolean, onSelected: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .height(IntrinsicSize.Min)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelected
            )
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(id = R.color.dark_gray),
                unselectedColor = colorResource(id = R.color.gray)
            ),
        )
        Text(
            text = stringResource(
                id = R.string.product_price_title,
                PaymentUtils.formatPrice(price)
            ),
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = if (isSelected) colorResource(id = R.color.dark_gray) else colorResource(id = R.color.medium_gray)
        )
        VerticalDivider(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 12.dp),
        )
        Text(
            text = label,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = if (isSelected) colorResource(id = R.color.dark_gray) else colorResource(id = R.color.medium_gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChoiceItemSelected() {
    ChoiceItem(
        label = stringResource(id = R.string.payment_method_mock),
        price = 3.99,
        isSelected = true,
        onSelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChoiceItemUnselected() {
    ChoiceItem(
        label = stringResource(id = R.string.payment_method_mock),
        price = 3.99,
        isSelected = false,
        onSelected = {}
    )
}