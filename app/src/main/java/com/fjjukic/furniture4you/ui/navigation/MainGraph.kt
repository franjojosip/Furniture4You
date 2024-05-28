package com.fjjukic.furniture4you.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.home.Home
import com.fjjukic.furniture4you.ui.home.HomeViewModel
import com.fjjukic.furniture4you.ui.main.favorite.Favorite
import com.fjjukic.furniture4you.ui.main.favorite.FavoriteViewModel
import com.fjjukic.furniture4you.ui.main.notification.Notification
import com.fjjukic.furniture4you.ui.main.notification.NotificationViewModel
import com.fjjukic.furniture4you.ui.main.profile.Profile
import com.fjjukic.furniture4you.ui.main.profile.ProfileViewModel

@Composable
fun MainGraph(
    onProductClick: (String) -> Unit,
    onSearchClick: () -> Unit,
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
            val viewModel = hiltViewModel<HomeViewModel>()
            Home(viewModel, onProductClick, onCartClick, onSearchClick)
        }
        composable(Screens.MainScreen.Favorites.route) {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            Favorite(
                viewModel,
                onProductClicked = onProductClick,
                onSearchClicked = onSearchClick,
                onCartClicked = onCartClick
            )
        }
        composable(Screens.MainScreen.Notification.route) {
            val viewModel = hiltViewModel<NotificationViewModel>()
            Notification(
                viewModel,
                onSearchClicked = onSearchClick
            )
        }
        composable(Screens.MainScreen.Profile.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            Profile(
                viewModel,
                onSearchClick = {},
                onLogoutClick = {},
                onPaymentMethodClick = onPaymentMethodClick,
                onMyReviewsClick = onMyReviewsClick,
                onSettingsClick = onSettingsClick,
                onShippingClick = onShippingClick,
                onMyOrderClick = onMyOrderClick,
            )
        }
    }
}