package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.authNavigationGraph
import com.fjjukic.furniture4you.ui.navigation.homeGraph
import com.fjjukic.furniture4you.ui.navigation.rememberFurnitureNavController
import com.fjjukic.furniture4you.ui.theme.Furniture4YouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val furnitureNavController = rememberFurnitureNavController()
            val viewmodel = hiltViewModel<MainViewModel>()
            val startDestination = if (viewmodel.isLoggedIn) Graph.HOME else Graph.AUTH

            val navigateRoute by viewmodel.navigateRoute.collectAsState()

            Furniture4YouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(navigateRoute) {
                        if (navigateRoute.isNotEmpty()) {
                            furnitureNavController.navController.navigate(navigateRoute) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                    NavHost(
                        navController = furnitureNavController.navController,
                        route = Graph.ROOT,
                        startDestination = startDestination
                    ) {
                        authNavigationGraph(furnitureNavController.navController)
                        homeGraph(
                            navHostController = furnitureNavController.navController,
                            onNavigateToBottomBarRoute = furnitureNavController::onNavigateToBottomBarRoute
                        )
                    }
                }
            }
        }
    }
}