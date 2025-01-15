package com.fjjukic.furniture4you.ui.dialog

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
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.checkout.PaymentInfo
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.utils.CardNumberTransformation
import com.fjjukic.furniture4you.ui.common.utils.ExpDateTransformation
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.theme.GelatioTypography

@Composable
fun PaymentInfoDialog(
    paymentInfo: PaymentInfo,
    onContinueClick: (PaymentInfo) -> Unit,
    onDismissClick: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        CardInformationDialog(paymentInfo, onDismissClick, onContinueClick)
    }
}

@Preview
@Composable
fun CardInformationDialogPreview() {
    CardInformationDialog(
        paymentInfo = MockRepository.getPaymentInfo(),
        onDismissClick = {},
        onContinueClick = {}
    )
}

@Composable
fun CardInformationDialog(
    paymentInfo: PaymentInfo,
    onDismissClick: () -> Unit,
    onContinueClick: (PaymentInfo) -> Unit,
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
            modifier = modifier.background(colorResource(id = R.color.color_white))
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                text = stringResource(R.string.label_card_information),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.color_medium_gray),
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                value = cardHolder,
                label = stringResource(R.string.field_card_holder),
                placeholder = stringResource(R.string.placeholder_card_holder),
                onValueChange = {
                    cardHolder = it
                    true
                },
                isFieldValid = {
                    it.isNotBlank()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )

            OutlinedInputField(
                modifier = Modifier.padding(horizontal = 24.dp),
                value = cardNumber,
                label = stringResource(R.string.field_card_number),
                placeholder = stringResource(R.string.placeholder_card_number),
                onValueChange = {
                    if (it.length <= paymentInfo.maxCardNum && it.isDigitsOnly()) {
                        cardNumber = it
                        true
                    } else false
                },
                isFieldValid = {
                    PaymentUtils.isValidCardNumber(it)
                },
                errorMessage = stringResource(id = R.string.error_invalid_card_number),
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
                    label = stringResource(R.string.field_card_cvv),
                    placeholder = stringResource(R.string.placeholder_card_cvv),
                    onValueChange = {
                        if (it.length <= 3 && it.isDigitsOnly()) {
                            cvv = it
                            true
                        } else false
                    },
                    isFieldValid = {
                        PaymentUtils.isValidCVV(it)
                    },
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
                    label = stringResource(R.string.field_card_exp_date),
                    placeholder = stringResource(R.string.placeholder_card_exp_date),
                    onValueChange = {
                        if (it.length <= 4 && it.isDigitsOnly()) {
                            expDate = it
                            true
                        } else false
                    },
                    isFieldValid = {
                        PaymentUtils.isValidExpDate(it)
                    },
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
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDismissClick) {
                    Text(
                        stringResource(id = R.string.btn_cancel),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_gray),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    if (PaymentUtils.isValidCardInformation(cardHolder, cardNumber, cvv, expDate)) {
                        onContinueClick(
                            PaymentInfo(cardHolder, cardNumber, cvv, expDate)
                        )
                    }
                }) {
                    Text(
                        stringResource(id = R.string.btn_confirm),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.color_black),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}