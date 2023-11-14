package com.fjjukic.furniture4you.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 30.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(64.dp)
            )
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(end = 30.dp)
            )
        }
        Text(
            text = "Hello!",
            fontSize = 30.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.2.sp,
            color = Color(0xFF606060),
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 24.dp, end = 24.dp, top = 30.dp)
        )
        Text(
            text = "Welcome back".uppercase(),
            fontSize = 24.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF303030),
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 30.dp
            ),
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 100.dp)
        ) {
            Text(
                text = "Email",
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = Color(0xFF909090),
                modifier = modifier
                    .padding(start = 24.dp, end = 24.dp, top = 30.dp)
            )
            EmailInputField()
            Text(
                text = "Password",
                fontSize = 14.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = Color(0xFF909090),
                modifier = modifier
                    .padding(start = 24.dp, end = 24.dp, top = 30.dp)
            )
            PasswordInputField()

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(hint: String, value: String, onTextChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { onTextChange(it) },
        label = { Text(hint) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Preview
@Composable
fun EmailTextFieldPreview() {
    EmailInputField()
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

@Composable
fun EmailInputField() {
    var email by remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        InputTextField(hint = "Email", value = email, onTextChange = { email = it })

        if (email.isNotEmpty() && !isValidEmail(email)) {
            Text(text = "Email is not valid", color = Color.Red)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField() {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(id = R.drawable.ic_visibility_on)
        else
            painterResource(id = R.drawable.ic_visibility_off)

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }
}