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
        data object Notification : MainScreen("notification_route")
        data object Profile : MainScreen("profile_route")
    }

    data object Search : Screens("search_route")

    data object Cart : Screens("cart_route")
    data object Checkout : Screens("checkout_route")
    data object SuccessOrder : Screens("success_order_route")

    data object MyOrder : Screens("my_order_route")

    data object ShippingAddressSetting : Screens("shipping_address_setting_route")
    data object AddShippingAddress : Screens("add_shipping_address_route")

    data object PaymentMethod : Screens("payment_method_route")
    data object AddPaymentMethod : Screens("add_payment_method_route")

    data object RatingReview : Screens("rating_review_route")
    data object MyReview : Screens("my_review_route")

    data object Settings : Screens("settings_route")


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