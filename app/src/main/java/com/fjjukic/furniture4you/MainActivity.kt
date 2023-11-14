package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.fjjukic.furniture4you.ui.auth.LoginScreen
import com.fjjukic.furniture4you.ui.theme.Furniture4YouTheme
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Furniture4YouTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //StartScreen()
                    LoginScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview(
    modifier: Modifier = Modifier
) {
    StartScreen()
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.prelogin_background),
            contentDescription = "Prelogin background",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.matchParentSize()
        )
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Make your".uppercase(),
                fontSize = 24.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = Color(0xFF606060),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp, end = 24.dp, top = 250.dp)
            )
            Text(
                text = "Home beautiful".uppercase(),
                fontSize = 30.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp, start = 24.dp, end = 24.dp)
            )
            Text(
                text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                color = Color(0xFF808080),
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(top = 32.dp, start = 60.dp, end = 24.dp)
            )
            Button(
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
                modifier = modifier
                    .padding(top = 200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    fontFamily = gelatioFamily,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )
    }
}