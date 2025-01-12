package com.fjjukic.furniture4you.ui.auth.login

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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.crypto.BiometricsHelper
import com.fjjukic.furniture4you.ui.common.fields.EmailInputField
import com.fjjukic.furniture4you.ui.common.fields.PasswordInputField
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.common.utils.findActivity
import com.fjjukic.furniture4you.ui.components.FullscreenProgressBar
import com.fjjukic.furniture4you.ui.components.Header
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onForgotPasswordClick = {},
        onRegisterClick = {},
        onAuthenticated = {}
    )
}

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onAuthenticated: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val activity = LocalContext.current.findActivity()

    LaunchedEffect(state.messageResId) {
        state.messageResId?.let { resId ->
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onAuthenticated()
        }
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.color_white))
                .verticalScroll(rememberScrollState())
        ) {
            Header(
                title = stringResource(R.string.title_login),
                subtitle = stringResource(R.string.subtitle_login)
            )
            LoginForm(
                onForgotPasswordClick = onForgotPasswordClick,
                onRegisterClick = onRegisterClick,
                onLoginClick = { email, password ->
                    viewModel.login(email, password)
                },
                showBiometricIcon = state.isBiometricAvailable,
                onBiometricActionClick = viewModel::onBiometricActionClick
            )
        }
        if (state.isLoading) {
            FullscreenProgressBar()
        }

        if (state.biometricsPromptData != null) {
            BiometricsHelper.authenticateUser(
                state.biometricsPromptData.cipher,
                activity as FragmentActivity,
                onSuccess = viewModel::onBiometricLoginSuccess,
            )
        }
    }
}

@Preview
@Composable
fun LoginFormPreview() {
    LoginForm(
        onLoginClick = { _, _ -> },
        onForgotPasswordClick = {},
        onRegisterClick = {},
        onBiometricActionClick = {}
    )
}

@Composable
fun LoginForm(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    showBiometricIcon: Boolean = false,
    onBiometricActionClick: () -> Unit = {},
) {
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.color_white),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .wrapContentSize()
            .padding(24.dp)
    ) {
        EmailInputField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.padding(top = 24.dp),
            showBiometricIcon = showBiometricIcon,
            onBiometricActionClick = onBiometricActionClick
        )

        PasswordInputField(
            value = password,
            onValueChange = {
                password = it
                true
            },
            isFieldValid = {
                ValidationUtils.isPasswordValid(it)
            },
            onDone = { onLoginClick(email, password) },
            isLastField = true,
            modifier = Modifier.padding(top = 12.dp)
        )

        ClickableText(
            text = AnnotatedString(stringResource(R.string.btn_forgot_password)),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.color_medium_gray),
            ),
            onClick = { onForgotPasswordClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
        )

        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
            modifier = modifier
                .padding(top = 40.dp)
                .width(260.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                onLoginClick(email, password)
            }
        ) {
            Text(
                text = stringResource(R.string.btn_login),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.color_white),
                modifier = Modifier.padding(6.dp)
            )
        }

        ClickableText(
            text = AnnotatedString(stringResource(R.string.btn_sign_up).uppercase()),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.color_dark_gray),
            ),
            onClick = { onRegisterClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, bottom = 40.dp)
        )
    }
}