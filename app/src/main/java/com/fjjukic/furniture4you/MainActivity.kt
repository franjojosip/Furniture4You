package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjjukic.furniture4you.ui.cart.Cart
import com.fjjukic.furniture4you.ui.cart.CartViewModel
import com.fjjukic.furniture4you.ui.main.MainScreen
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.navigation.authNavigationGraph
import com.fjjukic.furniture4you.ui.productdetail.ProductDetail
import com.fjjukic.furniture4you.ui.productdetail.ProductDetailViewModel
import com.fjjukic.furniture4you.ui.theme.Furniture4YouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController: NavHostController = rememberNavController()

            Furniture4YouTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navHostController,
                        route = Graph.ROOT,
                        startDestination = Screens.Cart.route //CHANGE TO AUTH
                    ) {
                        composable(route = Graph.MAIN) {
                            MainScreen(
                                onProductClick = { productId ->
                                    navHostController.navigate(
                                        Screens.ProductDetail.getRouteWithArg(productId)
                                    )
                                },
                                onCartClick = {
                                    navHostController.navigate(
                                        Screens.Cart.route
                                    )
                                }
                            )
                        }
                        composable(
                            route = Screens.ProductDetail.routeWithArgs,
                            arguments = Screens.ProductDetail.arguments
                        ) {
                            ProductDetail(viewModel = ProductDetailViewModel(),
                                onBackClicked = {
                                    navHostController.popBackStack()
                                },
                                onNavigateToCartClicked = {
                                    navHostController.navigate(
                                        Screens.Cart.route
                                    )
                                })
                        }
                        composable(route = Screens.Cart.route) {
                            val viewModel = hiltViewModel<CartViewModel>()
                            Cart(
                                viewModel,
                                onProductClicked = { productId ->
                                    navHostController.navigate(
                                        Screens.ProductDetail.getRouteWithArg(productId)
                                    )
                                },
                                onBackClicked = {
                                    navHostController.popBackStack()
                                },
                                onCheckoutClicked = {
//                                    navHostController.navigate(
//                                        Screens.Checkout.route
//                                    )
                                }
                            )
                        }
                        authNavigationGraph(navHostController)
                    }
                }
            }
        }
    }
}