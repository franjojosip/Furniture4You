package com.fjjukic.furniture4you.ui.common.fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.theme.FieldTextColor
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLastField: Boolean = false,
    labelResId: Int = R.string.field_password,
    placeholderResId: Int = R.string.field_password
) {
    var isError by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = painterResource(
        id = if (passwordVisibility) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(labelResId),
            fontSize = 14.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = FieldTextColor
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = value,
            onValueChange = {
                onValueChange(it)
                isError = !ValidationUtils.isPasswordValid(it)
            },
            placeholder = {
                Text(
                    text = stringResource(placeholderResId),
                    color = colorResource(id = R.color.color_placeholder)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = stringResource(R.string.content_desc_visibility_icon)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = if (isLastField) ImeAction.Done else ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        if (isError) {
            ErrorField(errorMessage = stringResource(R.string.error_invalid_password))
        }
    }
}