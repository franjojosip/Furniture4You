package com.fjjukic.furniture4you.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.fjjukic.furniture4you.ui.auth.ForgotPasswordScreen
import com.fjjukic.furniture4you.ui.auth.LoginScreen
import com.fjjukic.furniture4you.ui.auth.RegisterScreen
import com.fjjukic.furniture4you.ui.common.PreloginScreen

fun NavGraphBuilder.authNavigationGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = Screens.AuthScreen.Prelogin.route
    ) {

        composable(Screens.AuthScreen.Prelogin.route) {
            PreloginScreen {
                navHostController.navigate(Screens.AuthScreen.Login.route) {
                    popUpTo(Screens.AuthScreen.Prelogin.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
        }
        composable(Screens.AuthScreen.Login.route) {
            LoginScreen(
                onForgotPasswordClicked = {
                    navHostController.navigate(Screens.AuthScreen.ForgotPassword.route)
                }, onRegisterClicked = {
                    navHostController.navigate(Screens.AuthScreen.Register.route)
                }, onLoginClicked = {
                    navHostController.navigate(Graph.MAIN)
                }
            )
        }
        composable(Screens.AuthScreen.Register.route) {
            RegisterScreen {
                navHostController.popBackStack(Screens.AuthScreen.Login.route, false)
            }
        }
        composable(Screens.AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen {
                navHostController.popBackStack(Screens.AuthScreen.Login.route, false)
            }
        }
    }
}