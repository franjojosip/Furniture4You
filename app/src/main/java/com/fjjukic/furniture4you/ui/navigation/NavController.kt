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
        data object Prelogin : AuthScreen("prelogin_route")
        data object Login : AuthScreen("login_route")
        data object Register : AuthScreen("register_route")
        data object ForgotPassword : AuthScreen("forgot_password_route")
    }

    sealed class HomeSections(route: String) : Screens(route) {
        data object Home : HomeSections("home/home_route")
        data object Favorites : HomeSections("home/favorites_route")
        data object Notification : HomeSections("home/notification_route")
        data object Profile : HomeSections("home/profile_route")
    }

    sealed class ProfileSections(route: String) : Screens(route) {
        data object MyOrders : HomeSections("${Profile.route}/my_orders_route")
        data object ShippingAddress : HomeSections("${Profile.route}/shipping_address_route")
        data object PaymentMethod : HomeSections("${Profile.route}/payment_method_route")
        data object MyReviews : HomeSections("${Profile.route}/my_review_route")
        data object Settings : HomeSections("${Profile.route}/settings_route")
    }

    data object Search : Screens("search_route")

    data object Cart : Screens("cart_route")
    data object Checkout : Screens("checkout_route")

    data object SuccessOrder : Screens("success_order_route")

    data object ShippingAddressAdd : Screens("shipping_address_add_route")
    data object PaymentMethodAdd : Screens("payment_method_add_route")

    data object RatingReview : Screens("rating_review_route")

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