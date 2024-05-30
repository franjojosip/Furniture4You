package com.fjjukic.furniture4you.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.fjjukic.furniture4you.ui.auth.ForgotPasswordScreen
import com.fjjukic.furniture4you.ui.auth.LoginScreen
import com.fjjukic.furniture4you.ui.auth.RegisterScreen
import com.fjjukic.furniture4you.ui.main.PreloginScreen

fun NavGraphBuilder.authNavigationGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = Screens.AuthScreen.Prelogin.route
    ) {

        composable(Screens.AuthScreen.Prelogin.route) {
            PreloginScreen(
                onContinueClick = {
                    navHostController.navigate(Screens.AuthScreen.Login.route) {
                        popUpTo(Screens.AuthScreen.Prelogin.route) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            )
        }
        composable(Screens.AuthScreen.Login.route) {
            LoginScreen(
                onForgotPasswordClick = {
                    navHostController.navigate(Screens.AuthScreen.ForgotPassword.route)
                }, onRegisterClick = {
                    navHostController.navigate(Screens.AuthScreen.Register.route)
                }, onLoginClick = {
                    navHostController.navigate(Graph.MAIN) {
                        popUpTo(Graph.AUTH) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            )
        }
        composable(Screens.AuthScreen.Register.route) {
            RegisterScreen(
                onLoginClick = {
                    navHostController.popBackStack(Screens.AuthScreen.Login.route, false)
                }
            )
        }
        composable(Screens.AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onLoginClick = {
                    navHostController.popBackStack(Screens.AuthScreen.Login.route, false)
                }
            )
        }
    }
}