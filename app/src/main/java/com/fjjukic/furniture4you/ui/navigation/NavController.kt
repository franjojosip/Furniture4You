package com.fjjukic.furniture4you.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

sealed class Screens(val route: String) {
    sealed class AuthScreen(route: String) : Screens(route) {
        data object Prelogin : AuthScreen("prelogin_route")
        data object Login : AuthScreen("login_route")
        data object Register : AuthScreen("register_route")
        data object ForgotPassword : AuthScreen("forgot_password_route")
    }

    sealed class MainScreen(route: String) : Screens(route) {
        data object Home : MainScreen("home_route")
        data object Favorites : MainScreen("favorites_route")
        data object Notifications : MainScreen("notifications_route")
        data object Profile : MainScreen("profile_route")
    }

    data object ProductDetail : Screens("product_detail_route") {
        val productDetailArg = "product_id"
        val routeWithArgs = "${route}/{${productDetailArg}}"
        val arguments = listOf(
            navArgument(productDetailArg) { type = NavType.StringType }
        )

        fun getRouteWithArg(productId: String): String {
            return "${route}/${productId}"
        }
    }
}