package com.fjjukic.furniture4you.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.auth.enable_biometrics.EnableBiometricsScreen
import com.fjjukic.furniture4you.ui.common.CombinedClickableText
import com.fjjukic.furniture4you.ui.common.fields.EmailInputField
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.fields.PasswordInputField
import com.fjjukic.furniture4you.ui.components.FullscreenProgressBar
import com.fjjukic.furniture4you.ui.components.Header
import com.fjjukic.furniture4you.ui.setting.SwitchField
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onRegistered: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.messageResId) {
        state.messageResId?.let { resId ->
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(key1 = state.isRegistered) {
        if (state.isRegistered) {
            onRegistered()
        }
    }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.color_white))
                .verticalScroll(rememberScrollState())
        ) {
            Header(subtitle = stringResource(R.string.title_register))
            RegisterForm(
                onLoginClick = onLoginClick,
                onRegisterClick = { name, email, password, confirmPassword, shouldCheckBiometrics ->
                    viewModel.register(
                        name,
                        email,
                        password,
                        confirmPassword,
                        shouldCheckBiometrics
                    )
                },
                isBiometricsAvailable = state.isBiometricsAvailable
            )
        }
        if (state.isLoading) {
            FullscreenProgressBar()
        }
        if (state.shouldRequestBiometrics) {
            EnableBiometricsScreen(
                onSuccess = viewModel::onBiometricsSuccess,
                onSkipClick = onRegistered,
                onExitClick = {
                    onLoginClick()
                }
            )
        }
    }
}

@Preview
@Composable
fun RegisterFormPreview() {
    RegisterForm(
        onLoginClick = {},
        onRegisterClick = { _, _, _, _, _ -> },
        isBiometricsAvailable = true
    )
}

@Composable
fun RegisterForm(
    onLoginClick: () -> Unit,
    onRegisterClick: (String, String, String, String, Boolean) -> Unit,
    isBiometricsAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var shouldCheckBiometrics by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.color_white),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(24.dp)
    ) {
        OutlinedInputField(
            value = name,
            label = stringResource(R.string.field_name),
            placeholder = stringResource(R.string.placeholder_name),
            onValueChange = { name = it },
            isFieldValid = { it.isNotBlank() },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
        )

        EmailInputField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.padding(top = 12.dp)
        )

        PasswordInputField(
            value = password,
            onValueChange = { password = it },
            isLastField = false,
            modifier = Modifier.padding(top = 12.dp)
        )

        PasswordInputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isLastField = true,
            labelResId = R.string.field_confirm_password,
            placeholderResId = R.string.placeholder_password,
            modifier = Modifier.padding(top = 12.dp),
        )

        if (isBiometricsAvailable) {
            SwitchField(
                stringResource(id = R.string.label_use_biometrics),
                onCheckedChange = {
                    shouldCheckBiometrics = it
                },
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .padding(top = 20.dp),
                defaultState = false,
                isTitle = false
            )
        }

        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
            onClick = {
                onRegisterClick(name, email, password, confirmPassword, shouldCheckBiometrics)
            },
            modifier = modifier
                .padding(top = 40.dp)
                .width(260.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.btn_sign_up).uppercase(),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.color_white),
                modifier = Modifier.padding(6.dp)
            )
        }
        CombinedClickableText(
            startTextResId = R.string.label_register_have_account,
            endTextResId = R.string.btn_sign_up,
            onClick = { onLoginClick() },
            modifier = Modifier
                .padding(vertical = 30.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}