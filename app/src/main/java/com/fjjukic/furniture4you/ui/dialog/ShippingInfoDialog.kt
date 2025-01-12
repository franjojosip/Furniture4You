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
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.GelatioTypography

@Composable
fun ShippingInfoDialog(
    shippingInfo: ShippingInfo,
    onContinueClicked: (ShippingInfo) -> Unit,
    onDismissClicked: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true
) {
    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        ShippingInformationDialog(shippingInfo, onDismissClicked, onContinueClicked)
    }
}

@Preview
@Composable
fun ShippingInformationDialogPreview() {
    ShippingInformationDialog(
        shippingInfo = MockRepository.getShippingInfo(),
        onDismissClicked = {},
        onContinueClick = {}
    )
}

@Composable
fun ShippingInformationDialog(
    shippingInfo: ShippingInfo,
    onDismissClicked: () -> Unit,
    onContinueClick: (ShippingInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf(shippingInfo.fullName) }
    var address by remember { mutableStateOf(shippingInfo.address) }

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
                text = stringResource(R.string.title_shipping_information),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.color_medium_gray),
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                value = fullName,
                label = stringResource(R.string.field_shipping_fullname),
                placeholder = stringResource(R.string.placeholder_name),
                onValueChange = {
                    fullName = it
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
                value = address,
                label = stringResource(R.string.field_shipping_address),
                placeholder = stringResource(R.string.placeholder_shipping_address),
                onValueChange = {
                    address = it
                    true
                },
                isFieldValid = {
                    it.isNotBlank()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDismissClicked) {
                    Text(
                        stringResource(id = R.string.btn_cancel),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_gray),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    if (fullName.isNotEmpty() && address.isNotEmpty()) {
                        onContinueClick(
                            shippingInfo.copy(
                                fullName = fullName,
                                address = address
                            )
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