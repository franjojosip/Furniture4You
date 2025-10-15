package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fjjukic.furniture4you.ui.common.prelogin.PreloginScreen
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.navigation.authNavigationGraph
import com.fjjukic.furniture4you.ui.navigation.homeGraph
import com.fjjukic.furniture4you.ui.navigation.rememberFurnitureNavController
import com.fjjukic.furniture4you.ui.theme.Furniture4YouTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val furnitureNavController = rememberFurnitureNavController()
            val systemUiController = rememberSystemUiController()

            Furniture4YouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()


                    val viewModel = hiltViewModel<MainViewModel>()
                    val startDestination = viewModel.getStartDestination()

                    if (startDestination == Screens.Prelogin.route) {
                        SideEffect {
                            WindowCompat.setDecorFitsSystemWindows(window, false)
                            systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
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
                                            }
                                        }
                                    }
                                )
                            }
                            authNavigationGraph(furnitureNavController.navController, systemUiController)
                            homeGraph(
                                navHostController = furnitureNavController.navController,
                                onNavigateToBottomBarRoute = furnitureNavController::onNavigateToBottomBarRoute,
                                snackbarHostState,
                                scope,
                                systemUiController
                            )
                        }
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}