package com.fjjukic.furniture4you.ui.common.fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.FieldTextColor
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview(showBackground = true)
@Composable
fun OutlinedInputFieldPreview() {
    OutlinedInputField(
        value = "",
        label = stringResource(id = R.string.placeholder_name),
        placeholder = stringResource(id = R.string.placeholder_name),
        isFieldValid = { true },
        onValueChange = {},
        errorMessage = stringResource(id = R.string.placeholder_name)
    )
}

@Composable
fun OutlinedInputField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isFieldValid: (String) -> Boolean,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = FieldTextColor,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange(it)
                isError = !isFieldValid(it)
            },
            placeholder = {
                Text(text = placeholder, color = colorResource(id = R.color.color_placeholder))
            },
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )

        if (isError) {
            ErrorField(errorMessage = errorMessage ?: stringResource(R.string.error_invalid_field))
        }
    }
}