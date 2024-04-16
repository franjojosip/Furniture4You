package com.fjjukic.furniture4you.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.home.HomeScreen

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
            HomeScreen(onProductClick)
        }
        composable(Screens.MainScreen.Favorites.route) {
            HomeScreen(onProductClick)
        }
        composable(Screens.MainScreen.Notifications.route) {
            HomeScreen(onProductClick)
        }
        composable(Screens.MainScreen.Profile.route) {
            HomeScreen(onProductClick)
        }
    }
}