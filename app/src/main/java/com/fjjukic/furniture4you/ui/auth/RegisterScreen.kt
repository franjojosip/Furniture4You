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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.common.Header
import com.fjjukic.furniture4you.ui.common.OutlinedInputField
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen {}
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Header(subtitle = stringResource(R.string.register_title))
        RegisterForm(onLoginClicked = onLoginClicked)
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 26.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        OutlinedInputField(
            modifier = Modifier.padding(top = 24.dp),
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.field_name)
        )

        EmailInputField(Modifier.padding(top = 12.dp), email) {
            email = it
        }

        PasswordInputField(Modifier.padding(top = 12.dp), password) {
            password = it
        }
        PasswordInputField(
            Modifier.padding(top = 12.dp),
            confirmPassword,
            stringResource(R.string.field_confirm_password)
        ) {
            confirmPassword = it
        }

        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
            modifier = modifier
                .padding(top = 40.dp)
                .width(260.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                // Login logic
            }) {
            Text(
                text = stringResource(R.string.login_button_sign_up).uppercase(),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(6.dp)
            )
        }

        ClickableText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, bottom = 40.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF808080),
                    )
                ) {
                    append(stringResource(R.string.register_have_account))
                }
                append(" ")
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontFamily = gelatioFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF303030),
                    )
                ) {
                    append(stringResource(R.string.register_button_sign_in).uppercase())
                }
            }, onClick = {
                onLoginClicked.invoke()
            })
    }
}
