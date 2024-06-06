package com.fjjukic.furniture4you.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.fjjukic.furniture4you.ui.auth.ForgotPasswordScreen
import com.fjjukic.furniture4you.ui.auth.LoginScreen
import com.fjjukic.furniture4you.ui.auth.RegisterScreen
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel

fun NavGraphBuilder.authNavigationGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Graph.AUTH,
        startDestination = Screens.AuthScreen.Login.route
    ) {
        composable(Screens.AuthScreen.Login.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LoginScreen(
                onForgotPasswordClick = {
                    navHostController.navigate(Screens.AuthScreen.ForgotPassword.route)
                }, onRegisterClick = {
                    navHostController.navigate(Screens.AuthScreen.Register.route)
                }, onLoginClick = { email, password ->
                    viewModel.login(email, password).also {
                        navHostController.navigate(Graph.HOME) {
                            popUpTo(Screens.AuthScreen.Login.route) {
                                inclusive = true
                                saveState = true
                            }
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