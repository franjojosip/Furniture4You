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
    onProductClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onCartClick: () -> Unit,
    onPaymentMethodClick: () -> Unit,
    onMyReviewsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onShippingClick: () -> Unit,
    onMyOrderClick: () -> Unit,
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
            HomeScreen(onProductClick, onCartClick, onSearchClick)
        }
        composable(Screens.MainScreen.Favorites.route) {
            FavoriteScreen(
                onProductClick = onProductClick,
                onSearchClick = onSearchClick,
                onCartClick = onCartClick
            )
        }
        composable(Screens.MainScreen.Notification.route) {
            NotificationScreen(
                onSearchClick = onSearchClick
            )
        }
        composable(Screens.MainScreen.Profile.route) {
            ProfileScreen(
                onSearchClick = onSearchClick,
                onLogoutClick = onLogoutClick,
                onPaymentMethodClick = onPaymentMethodClick,
                onMyReviewsClick = onMyReviewsClick,
                onSettingsClick = onSettingsClick,
                onShippingClick = onShippingClick,
                onMyOrderClick = onMyOrderClick,
            )
        }
    }
}