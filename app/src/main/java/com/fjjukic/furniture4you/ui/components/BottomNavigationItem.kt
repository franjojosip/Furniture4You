package com.fjjukic.furniture4you.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.fjjukic.furniture4you.ui.navigation.Screens
import ht.ferit.fjjukic.foodlovers.R

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    @Composable
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_home),
                icon = Icons.Filled.Home,
                route = Screens.MainScreen.Home.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_favorites),
                icon = Icons.Filled.Favorite,
                route = Screens.MainScreen.Favorites.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_notifications),
                icon = Icons.Filled.Notifications,
                route = Screens.MainScreen.Notifications.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_profile),
                icon = Icons.Filled.Person,
                route = Screens.MainScreen.Profile.route
            ),
        )
    }
}