package com.fjjukic.furniture4you.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.common.Header
import com.fjjukic.furniture4you.ui.common.OutlinedInputField
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(onForgotPasswordClicked = {}, onRegisterClicked = {})
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onForgotPasswordClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Header()
        Text(
            text = stringResource(R.string.login_title),
            fontSize = 30.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.2.sp,
            color = Color(0xFF606060),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp, end = 24.dp, top = 30.dp)
        )
        Text(
            text = stringResource(R.string.login_subtitle).uppercase(),
            fontSize = 24.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF303030),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
        )
        LoginForm(modifier, onForgotPasswordClicked, onRegisterClicked)
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    onForgotPasswordClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
            .padding(top = 36.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        OutlinedInputField(
            modifier = Modifier.padding(top = 24.dp),
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.field_email),
            shouldShowError = email.isNotEmpty() && !isValidEmail(email),
            errorMessage = stringResource(R.string.error_invalid_email)
        )
        PasswordInputField(Modifier.padding(top = 12.dp), password) {
            password = it
        }

        ClickableText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
            text = AnnotatedString(stringResource(R.string.login_button_forgot_password)),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF303030),
            ), onClick = {
                onForgotPasswordClicked.invoke()
            })

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
                text = stringResource(R.string.login_button),
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
            text = AnnotatedString(stringResource(R.string.login_button_sign_up).uppercase()),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF303030),
            ), onClick = {
                onRegisterClicked.invoke()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
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
            color = Color(0xFF909090)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(stringResource(R.string.field_email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        if (value.isNotEmpty() && !isValidEmail(value)) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp),
                text = stringResource(R.string.error_invalid_email),
                color = Color(0xD0B92828)
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    password: String,
    label: String = stringResource(R.string.field_password),
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility_on)
    else
        painterResource(id = R.drawable.ic_visibility_off)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF909090)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = password,
            onValueChange = onValueChange,
            placeholder = { Text(label) },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = stringResource(R.string.content_desc_visibility_icon)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        if (password.isNotEmpty() && !isValidPassword(password)) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp),
                text = stringResource(R.string.error_invalid_password),
                color = Color(0xD0B92828)
            )
        }
    }
}

private fun isValidPassword(value: String): Boolean {
    return value.isNotBlank() && value.length > 4 && value.length < 12
}