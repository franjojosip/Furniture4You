package com.fjjukic.furniture4you.ui.common.fields

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Preview(showBackground = true)
@Composable
fun EmailInputFieldPreview() {
    EmailInputField(
        value = stringResource(id = R.string.field_email),
        onValueChange = { _ -> },
        showBiometricIcon = true
    )
}

@Composable
fun EmailInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    showBiometricIcon: Boolean = false,
    onBiometricActionClick: () -> Unit = {}
) {
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.field_email),
            fontSize = 14.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.color_field_title)
        )
        Box(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            OutlinedTextField(
                trailingIcon = {
                    if (showBiometricIcon) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_fingerprint),
                            contentDescription = "Biometrics logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        onBiometricActionClick()
                                    }
                                ),
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.color_medium_gray)),
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("emailField"),
                value = value,
                onValueChange = {
                    onValueChange(it)
                    isError = !ValidationUtils.isEmailValid(it)
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.field_email),
                        color = colorResource(id = R.color.color_placeholder)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
        }

        if (isError) {
            ErrorField(errorMessage = stringResource(R.string.error_invalid_email))
        }
    }
}