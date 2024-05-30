package com.fjjukic.furniture4you.ui.common.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fjjukic.furniture4you.ui.navigation.Screens
import ht.ferit.fjjukic.foodlovers.R

data class BottomNavigationItem(
    val label: String = "",
    val selectedIcon: Int = R.drawable.ic_home,
    val unselectedIcon: Int = R.drawable.ic_home_black,
    val route: String = ""
) {
    @Composable
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_home),
                selectedIcon = R.drawable.ic_home_black,
                unselectedIcon = R.drawable.ic_home,
                route = Screens.MainScreen.Home.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_favorites),
                selectedIcon = R.drawable.ic_favorite_black,
                unselectedIcon = R.drawable.ic_favorite,
                route = Screens.MainScreen.Favorites.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_notification),
                selectedIcon = R.drawable.ic_notification_black,
                unselectedIcon = R.drawable.ic_notification,
                route = Screens.MainScreen.Notification.route
            ),
            BottomNavigationItem(
                label = stringResource(id = R.string.nav_profile),
                selectedIcon = R.drawable.ic_person_black,
                unselectedIcon = R.drawable.ic_person,
                route = Screens.MainScreen.Profile.route
            ),
        )
    }
}