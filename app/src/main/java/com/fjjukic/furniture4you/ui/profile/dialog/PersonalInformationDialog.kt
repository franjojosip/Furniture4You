package com.fjjukic.furniture4you.ui.profile.dialog

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
import com.fjjukic.furniture4you.ui.common.fields.EmailInputField
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.profile.PersonalInformation
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import ht.ferit.fjjukic.foodlovers.R

@Composable
fun PersonalInformationChangeDialog(
    shippingInfo: PersonalInformation,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    onContinueClicked: (PersonalInformation) -> Unit,
    onDismissClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        PersonalInformationDialog(shippingInfo, onDismissClicked, onContinueClicked)
    }
}

@Preview
@Composable
fun PersonalInformationDialogPreview() {
    PersonalInformationDialog(
        personalInformation = MockRepository.getPersonalInformation(),
        onDismissClicked = {},
        onContinueClicked = {}
    )
}

@Composable
fun PersonalInformationDialog(
    personalInformation: PersonalInformation,
    onDismissClicked: () -> Unit,
    onContinueClicked: (PersonalInformation) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(personalInformation.name) }
    var email by remember { mutableStateOf(personalInformation.email) }

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
                text = stringResource(R.string.personal_information),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.medium_gray),
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                value = name,
                placeholder = stringResource(R.string.field_name),
                onValueChange = {
                    name = it
                },
                isFieldValid = {
                    it.isNotBlank()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )

            EmailInputField(
                value = email,
                onValueChange = {
                    email = it
                }
            )

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
                    if (name.isNotEmpty() && email.isNotEmpty()) {
                        onContinueClicked(
                            PersonalInformation(name, email)
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