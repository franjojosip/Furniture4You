package com.fjjukic.furniture4you.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.home.HomeScreen

@Composable
fun MainGraph(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navHostController,
        route = Graph.MAIN,
        startDestination = Screens.MainScreen.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(Screens.MainScreen.Home.route) {
            HomeScreen()
        }
        composable(Screens.MainScreen.Favorites.route) {
            HomeScreen()
        }
        composable(Screens.MainScreen.Notifications.route) {
            HomeScreen()
        }
        composable(Screens.MainScreen.Profile.route) {
            HomeScreen()
        }
    }
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.MainScreen.Home.route
            ),
            BottomNavigationItem(
                label = "Favorites",
                icon = Icons.Filled.Favorite,
                route = Screens.MainScreen.Favorites.route
            ),
            BottomNavigationItem(
                label = "Notifications",
                icon = Icons.Filled.Notifications,
                route = Screens.MainScreen.Notifications.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                route = Screens.MainScreen.Profile.route
            ),
        )
    }
}