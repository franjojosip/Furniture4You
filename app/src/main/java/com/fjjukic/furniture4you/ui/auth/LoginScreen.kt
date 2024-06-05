package com.fjjukic.furniture4you.ui.auth

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.common.fields.EmailInputField
import com.fjjukic.furniture4you.ui.common.fields.PasswordInputField
import com.fjjukic.furniture4you.ui.components.Header
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(onForgotPasswordClick = {}, onRegisterClick = {}, onLoginClick = {})
}

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.color_white))
            .verticalScroll(scrollState)
    ) {
        Header(
            title = stringResource(R.string.title_login),
            subtitle = stringResource(R.string.subtitle_login)
        )
        LoginForm(onForgotPasswordClick, onRegisterClick, onLoginClick)
    }
}

@Composable
fun LoginForm(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
            modifier = Modifier.padding(top = 24.dp)
        )

        PasswordInputField(
            value = password,
            onValueChange = { password = it },
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
            onClick = onLoginClick
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