package com.fjjukic.furniture4you.ui.checkout.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import com.fjjukic.furniture4you.ui.checkout.PaymentInfo
import com.fjjukic.furniture4you.ui.common.CardNumberTransformation
import com.fjjukic.furniture4you.ui.common.ExpDateTransformation
import com.fjjukic.furniture4you.ui.common.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.PaymentUtils
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import ht.ferit.fjjukic.foodlovers.R

@Composable
fun PaymentInfoDialog(
    paymentInfo: PaymentInfo,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    onContinueClicked: (PaymentInfo) -> Unit,
    onDismissClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        CardInformationDialog(paymentInfo, onDismissClicked, onContinueClicked)
    }
}

@Preview
@Composable
fun CardInformationDialogPreview() {
    CardInformationDialog(
        paymentInfo = MockRepository.getPaymentInfo(),
        onDismissClicked = {},
        onContinueClicked = {}
    )
}

@Composable
fun CardInformationDialog(
    paymentInfo: PaymentInfo,
    onDismissClicked: () -> Unit,
    onContinueClicked: (PaymentInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    var cardHolder by remember { mutableStateOf(paymentInfo.cardHolder) }
    var cardNumber by remember { mutableStateOf(paymentInfo.cardNumber) }
    var cvv by remember { mutableStateOf(paymentInfo.cvv) }
    var expDate by remember { mutableStateOf(paymentInfo.expDate) }

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier.background(Color.White)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                text = stringResource(R.string.card_information),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.medium_gray),
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                value = cardHolder,
                placeholder = stringResource(R.string.field_card_holder),
                onValueChange = {
                    cardHolder = it
                },
                shouldShowError = cardHolder.isBlank(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )

            OutlinedInputField(
                modifier = Modifier.padding(horizontal = 24.dp),
                value = cardNumber,
                placeholder = stringResource(R.string.field_card_number),
                onValueChange = {
                    if (it.length <= paymentInfo.maxCardNum && it.isDigitsOnly()) {
                        cardNumber = it
                    }
                },
                shouldShowError = !PaymentUtils.isValidCardNumber(cardNumber),
                errorMessage = stringResource(id = R.string.invalid_card_number),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = CardNumberTransformation()
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedInputField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 20.dp),
                    value = cvv,
                    placeholder = stringResource(R.string.field_card_cvv),
                    onValueChange = {
                        if (it.length <= 3 && it.isDigitsOnly()) {
                            cvv = it
                        }
                    },
                    shouldShowError = !PaymentUtils.isValidCVV(cvv),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedInputField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                    value = expDate,
                    placeholder = stringResource(R.string.field_card_exp_date),
                    onValueChange = {
                        if (it.length <= 4 && it.isDigitsOnly()) {
                            expDate = it
                        }
                    },
                    shouldShowError = !PaymentUtils.isValidExpDate(expDate),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    visualTransformation = ExpDateTransformation(),
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .background(colorResource(id = R.color.color_dialog_bottom_background)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDismissClicked) {
                    Text(
                        stringResource(id = R.string.delivery_choice_btn_cancel),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    if (PaymentUtils.isValidCardInformation(cardHolder, cardNumber, cvv, expDate)) {
                        onContinueClicked(
                            PaymentInfo(cardHolder, cardNumber, cvv, expDate)
                        )
                    }
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