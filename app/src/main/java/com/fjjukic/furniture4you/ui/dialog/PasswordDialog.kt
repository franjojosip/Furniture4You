package com.fjjukic.furniture4you.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.fields.PasswordInputField
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.theme.GelatioTypography

@Composable
fun PasswordChangeDialog(
    password: String,
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
        PasswordDialog(password, onDismissClick, onContinueClick)
    }
}

@Preview
@Composable
fun PasswordDialogPreview() {
    PasswordDialog(
        password = stringResource(id = R.string.app_name),
        onDismissClicked = {},
        onContinueClick = {}
    )
}

@Composable
fun PasswordDialog(
    password: String,
    onDismissClicked: () -> Unit,
    onContinueClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf(password) }

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
                text = stringResource(R.string.label_password),
                style = GelatioTypography.titleMedium,
                color = colorResource(id = R.color.color_medium_gray),
            )

            PasswordInputField(
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    true
                },
                isFieldValid = {
                    ValidationUtils.isPasswordValid(it)
                },
                onDone = {}
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
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),

                        )
                }
                TextButton(onClick = {
                    if (newPassword.isNotEmpty()) {
                        onContinueClick(newPassword)
                    }
                }) {
                    Text(
                        stringResource(id = R.string.btn_confirm),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.color_black),
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}