package com.fjjukic.furniture4you.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Graph {
    const val ROOT = "root"
    const val AUTH = "auth"
    const val HOME = "home"
}

sealed class Screens(val route: String) {
    sealed class AuthScreen(route: String) : Screens(route) {
        data object Login : AuthScreen("${Graph.AUTH}/login")
        data object Register : AuthScreen("${Graph.AUTH}/register")
        data object ForgotPassword : AuthScreen("${Graph.AUTH}/forgot_password")
    }

    sealed class HomeSections(route: String) : Screens(route) {
        data object Home : HomeSections("${Graph.HOME}/home")
        data object Favorites : HomeSections("${Graph.HOME}/favorites")
        data object Notification : HomeSections("${Graph.HOME}/notification")
        data object Profile : HomeSections("${Graph.HOME}/profile")
    }

    sealed class ProfileSections(route: String) : Screens(route) {
        data object MyOrders : HomeSections("${Profile.route}/my_orders")
        data object ShippingAddress : HomeSections("${Profile.route}/shipping_address")
        data object PaymentMethod : HomeSections("${Profile.route}/payment_method")
        data object MyReviews : HomeSections("${Profile.route}/my_review")
        data object Settings : HomeSections("${Profile.route}/settings")
    }

    data object Prelogin : Screens("prelogin")

    data object ShippingAddressAdd :
        Screens("${ProfileSections.ShippingAddress}/shipping_address_add")

    data object PaymentMethodAdd : Screens("${ProfileSections.PaymentMethod}/payment_method_add")

    data object Search : Screens("search")
    data object Cart : Screens("cart")

    data object Checkout : Screens("checkout")
    data object SuccessOrder : Screens("success_order")

    data object RatingReview : Screens("rating_review")

    data object ProductDetail : Screens("product_detail") {
        private const val PRODUCT_DETAIL_ARG = "product_id"

        val routeWithArgs = "${route}/{${PRODUCT_DETAIL_ARG}}"
        val arguments = listOf(
            navArgument(PRODUCT_DETAIL_ARG) { type = NavType.StringType }
        )

        fun getRouteWithArg(productId: String): String {
            return "${route}/${productId}"
        }
    }
}