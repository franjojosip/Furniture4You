package com.fjjukic.furniture4you.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.utils.CardNumberTransformation
import com.fjjukic.furniture4you.ui.common.utils.ExpDateTransformation
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun AddPaymentMethodPreview() {
    AddPaymentMethod(PaymentMethodViewModel(), {})
}

@Composable
fun AddPaymentMethod(
    viewModel: PaymentMethodViewModel,
    onBackClick: () -> Unit
) {
    val mockCard by viewModel.mockCard.collectAsStateWithLifecycle()

    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expDate by remember { mutableStateOf("") }

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
                    onClick = {}
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_add_new_card).uppercase(),
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
            PaymentCardItem(
                card = mockCard,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 40.dp)
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                value = cardHolder,
                label = stringResource(R.string.field_card_holder),
                placeholder = stringResource(R.string.placeholder_card_holder),
                onValueChange = {
                    cardHolder = it
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
                    if (it.length <= 16 && it.isDigitsOnly()) {
                        cardNumber = it
                    }
                },
                isFieldValid = {
                    PaymentUtils.isValidCardNumber(it)
                },
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
                    label = stringResource(R.string.field_card_cvv),
                    placeholder = stringResource(R.string.placeholder_card_cvv),
                    onValueChange = {
                        if (it.length <= 3 && it.isDigitsOnly()) {
                            cvv = it
                        }
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
                        }
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
        }
    }
}