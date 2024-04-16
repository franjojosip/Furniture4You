package com.fjjukic.furniture4you.ui.navigation


object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
    const val TEST = "test_graph"
}

sealed class Screens(val route: String) {
    sealed class AuthScreen(route: String) : Screens(route) {
        object Prelogin : AuthScreen("prelogin_route")
        object Login : AuthScreen("login_route")
        object Register : AuthScreen("register_route")
        object ForgotPassword : AuthScreen("forgot_password_route")
    }

    sealed class MainScreen(route: String) : Screens(route) {
        object Home : MainScreen("home_route")
        object Favorites : MainScreen("favorites_route")
        object Notifications : MainScreen("notifications_route")
        object Profile : MainScreen("profile_route")
    }
}