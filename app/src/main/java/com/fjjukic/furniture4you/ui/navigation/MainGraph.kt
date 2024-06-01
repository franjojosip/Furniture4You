package com.fjjukic.furniture4you.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.home.HomeScreen
import com.fjjukic.furniture4you.ui.main.favorite.FavoriteScreen
import com.fjjukic.furniture4you.ui.main.notification.NotificationScreen
import com.fjjukic.furniture4you.ui.main.profile.ProfileScreen

@Composable
fun MainGraph(
    rootNavController: NavHostController,
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navHostController,
        route = Graph.MAIN,
        startDestination = Screens.MainScreen.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(Screens.MainScreen.Home.route) {
            HomeScreen(
                onProductClick = {
                    rootNavController.navigate(
                        Screens.ProductDetail.getRouteWithArg(it)
                    )
                },
                onCartClick = {
                    rootNavController.navigate(Screens.Cart.route)
                },
                onSearchClick = {
                    rootNavController.navigate(Screens.Search.route)
                }
            )
        }
        composable(Screens.MainScreen.Favorites.route) {
            FavoriteScreen(
                onProductClick = {
                    rootNavController.navigate(
                        Screens.ProductDetail.getRouteWithArg(it)
                    )
                },
                onSearchClick = {
                    rootNavController.navigate(Screens.Search.route)
                },
                onCartClick = {
                    rootNavController.navigate(Screens.Cart.route)
                }
            )
        }
        composable(Screens.MainScreen.Notification.route) {
            NotificationScreen(
                onSearchClick = {
                    rootNavController.navigate(Screens.Search.route)
                }
            )
        }
        composable(Screens.MainScreen.Profile.route) {
            ProfileScreen(
                onSearchClick = {
                    rootNavController.navigate(Screens.Search.route)
                },
                onLogoutClick = {
                    /* TODO */
                },
                onPaymentMethodClick = {
                    rootNavController.navigate(Screens.PaymentMethod.route)
                },
                onMyReviewsClick = {
                    rootNavController.navigate(Screens.MyReview.route)
                },
                onSettingsClick = {
                    rootNavController.navigate(Screens.Settings.route)
                },
                onShippingClick = {
                    rootNavController.navigate(Screens.ShippingAddress.route)
                },
                onMyOrderClick = {
                    rootNavController.navigate(Screens.MyOrder.route)
                },
            )
        }
    }
}