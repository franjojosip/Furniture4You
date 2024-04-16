package com.fjjukic.furniture4you.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fjjukic.furniture4you.ui.components.BottomNavigationItem
import com.fjjukic.furniture4you.ui.navigation.MainGraph

@Composable
fun MainScreen(
    onProductClick: (String) -> Unit
) {
    val navHostController: NavHostController = rememberNavController()
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        val icon =
                            if (index == navigationSelectedItem) navigationItem.selectedIcon else navigationItem.unselectedIcon
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            icon = {
                                Icon(
                                    painterResource(icon),
                                    contentDescription = navigationItem.label
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.White
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
    ) { paddingValues ->
        MainGraph(onProductClick, navHostController, paddingValues)
    }
}