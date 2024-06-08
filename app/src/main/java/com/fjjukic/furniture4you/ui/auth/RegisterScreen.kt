package com.fjjukic.furniture4you.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.CombinedClickableText
import com.fjjukic.furniture4you.ui.common.fields.EmailInputField
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.common.fields.PasswordInputField
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel
import com.fjjukic.furniture4you.ui.components.Header
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onLoginClick = {})
}

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.color_white))
            .verticalScroll(rememberScrollState())
    ) {
        Header(subtitle = stringResource(R.string.title_register))
        RegisterForm(onLoginClick)
    }
}

@Composable
fun RegisterForm(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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

        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
            onClick = {
                viewModel.register(name, email, password, confirmPassword)
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