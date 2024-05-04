package com.fjjukic.furniture4you.ui.common

import android.os.SystemClock
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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
        onValueChange = {},
        errorMessage = "Error message"
    )
}

@Composable
fun OutlinedInputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    shouldShowError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
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
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )

        if (shouldShowError) {
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

fun Modifier.debouncedClickable(
    debounceTime: Long = 1000L,
    onClick: () -> Unit
): Modifier {
    return this.composed {
        val clickable = debounced(debounceTime = debounceTime, onClick = { onClick() })
        this.clickable { clickable() }
    }
}

@Composable
fun DisableBackNavigation(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back navigation
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        onBackPressedDispatcher.addCallback(backCallback)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                backCallback.remove()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            backCallback.remove()
        }
    }
}