package com.fjjukic.furniture4you.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fjjukic.furniture4you.ui.components.BottomNavigationItem
import com.fjjukic.furniture4you.ui.navigation.MainGraph
import com.fjjukic.furniture4you.ui.navigation.Screens
import ht.ferit.fjjukic.foodlovers.R

@Composable
fun MainScreen(
    onProductClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onPaymentMethodClick: () -> Unit,
    onMyReviewsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onShippingClick: () -> Unit,
    onMyOrderClick: () -> Unit
) {
    val navHostController: NavHostController = rememberNavController()
    var navigationSelectedItem by rememberSaveable { mutableIntStateOf(0) }
    val backStackEntry = navHostController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                NavigationBar(
                    containerColor = colorResource(id = R.color.color_white),
                ) {
                    BottomNavigationItem().bottomNavigationItems()
                        .forEachIndexed { index, navigationItem ->
                            val selected =
                                navigationItem.route == backStackEntry.value?.destination?.route
                            val icon =
                                if (selected) navigationItem.selectedIcon else navigationItem.unselectedIcon
                            NavigationBarItem(
                                selected = selected,
                                icon = {
                                    if (!selected && navigationItem.route == Screens.MainScreen.Notification.route) {
                                        BadgedBox(badge = { Badge() }) {
                                            Icon(
                                                painterResource(icon),
                                                contentDescription = navigationItem.label
                                            )
                                        }
                                    } else {
                                        Icon(
                                            painterResource(icon),
                                            contentDescription = navigationItem.label
                                        )
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = colorResource(id = R.color.color_white)
                                ),
                                onClick = {
                                    navigationSelectedItem = index
                                    navHostController.navigate(navigationItem.route) {
                                        popUpTo(navHostController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                }
            }
        }
    ) { paddingValues ->
        MainGraph(
            onProductClick,
            onSearchClick,
            onCartClick,
            onPaymentMethodClick,
            onMyReviewsClick,
            onSettingsClick,
            onShippingClick,
            onMyOrderClick,
            navHostController,
            paddingValues
        )
    }
}