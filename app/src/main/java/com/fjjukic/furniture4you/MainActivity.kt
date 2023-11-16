package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjjukic.furniture4you.ui.auth.ForgotPasswordScreen
import com.fjjukic.furniture4you.ui.auth.LoginScreen
import com.fjjukic.furniture4you.ui.auth.RegisterScreen
import com.fjjukic.furniture4you.ui.common.PreloginScreen
import com.fjjukic.furniture4you.ui.theme.Furniture4YouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            Furniture4YouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "prelogin") {
                        composable("prelogin") {
                            PreloginScreen {
                                navController.navigate("login")
                            }
                        }
                        composable("login") {
                            LoginScreen(onForgotPasswordClicked = {
                                navController.navigate("forgot_password")
                            }, onRegisterClicked = {
                                navController.navigate("register")
                            })
                        }
                        composable("register") {
                            RegisterScreen {
                                navController.popBackStack("login", false)
                            }
                        }
                        composable("forgot_password") {
                            ForgotPasswordScreen {
                                navController.popBackStack("login", false)
                            }
                        }
                    }
                }
            }
        }
    }
}