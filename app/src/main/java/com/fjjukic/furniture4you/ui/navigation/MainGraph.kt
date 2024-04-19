package com.fjjukic.furniture4you.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.favorite.Favorite
import com.fjjukic.furniture4you.ui.home.Home
import com.fjjukic.furniture4you.ui.home.HomeViewModel

@Composable
fun MainGraph(
    onProductClick: (String) -> Unit,
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
            Home(viewModel, onProductClick)
        }
        composable(Screens.MainScreen.Favorites.route) {
            Favorite({}, {})
        }
        composable(Screens.MainScreen.Notifications.route) {
        }
        composable(Screens.MainScreen.Profile.route) {
        }
    }
}