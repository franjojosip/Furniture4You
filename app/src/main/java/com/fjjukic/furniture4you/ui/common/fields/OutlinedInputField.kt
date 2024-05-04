package com.fjjukic.furniture4you.ui.common.fields

import android.os.SystemClock
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.ErrorColor
import com.fjjukic.furniture4you.ui.theme.FieldTextColor
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun OutlinedInputFieldPreview() {
    OutlinedInputField(
        value = "",
        placeholder = "Preview",
        isFieldValid = { true },
        onValueChange = {},
        errorMessage = "Error message"
    )
}

@Composable
fun OutlinedInputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isFieldValid: (String) -> Boolean,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = placeholder,
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
            placeholder = { Text(placeholder) },
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )

        if (isError) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp),
                text = errorMessage ?: stringResource(R.string.error_invalid_field),
                fontSize = 12.sp,
                color = ErrorColor
            )
        }
    }
}

@Composable
inline fun debounced(crossinline onClick: () -> Unit, debounceTime: Long = 1000L): () -> Unit {
    var lastTimeClicked by remember { mutableLongStateOf(0L) }
    val onClickLambda: () -> Unit = {
        val now = SystemClock.uptimeMillis()
        if (now - lastTimeClicked > debounceTime) {
            onClick()
        }
        lastTimeClicked = now
    }
    return onClickLambda
}