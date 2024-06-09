package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel
import com.fjjukic.furniture4you.ui.main.PreloginScreen
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
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

            Furniture4YouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel = hiltViewModel<MainViewModel>()
                    val startDestination = viewModel.getStartDestination()

                    NavHost(
                        navController = furnitureNavController.navController,
                        route = Graph.ROOT,
                        startDestination = startDestination
                    ) {
                        composable(Screens.Prelogin.route) {
                            PreloginScreen(
                                onContinueClick = {
                                    furnitureNavController.navController.navigate(Graph.AUTH) {
                                        popUpTo(Screens.Prelogin.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                    }
                                }
                            )
                        }
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