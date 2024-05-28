package com.fjjukic.furniture4you

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjjukic.furniture4you.ui.cart.CartScreen
import com.fjjukic.furniture4you.ui.checkout.CheckoutScreen
import com.fjjukic.furniture4you.ui.main.MainScreen
import com.fjjukic.furniture4you.ui.main.profile.Settings
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.navigation.authNavigationGraph
import com.fjjukic.furniture4you.ui.order.MyOrder
import com.fjjukic.furniture4you.ui.order.SuccessOrder
import com.fjjukic.furniture4you.ui.payment.AddPaymentMethod
import com.fjjukic.furniture4you.ui.payment.PaymentMethod
import com.fjjukic.furniture4you.ui.productdetail.ProductDetail
import com.fjjukic.furniture4you.ui.productdetail.ProductDetailViewModel
import com.fjjukic.furniture4you.ui.rating.MyReview
import com.fjjukic.furniture4you.ui.rating.RatingReview
import com.fjjukic.furniture4you.ui.search.Search
import com.fjjukic.furniture4you.ui.shipping.AddShippingAddress
import com.fjjukic.furniture4you.ui.shipping.ShippingAddressSetting
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
                        startDestination = Graph.MAIN //CHANGE TO AUTH
                    ) {
                        composable(route = Graph.MAIN) {
                            MainScreen(
                                onProductClick = {
                                    navHostController.navigate(
                                        Screens.ProductDetail.getRouteWithArg(it)
                                    )
                                },
                                onSearchClick = {
                                    navHostController.navigate(Screens.Search.route)
                                },
                                onCartClick = {
                                    navHostController.navigate(Screens.Cart.route)
                                },
                                onPaymentMethodClick = {
                                    navHostController.navigate(Screens.PaymentMethod.route)
                                },
                                onMyReviewsClick = {
                                    navHostController.navigate(Screens.MyReview.route)
                                },
                                onSettingsClick = {
                                    navHostController.navigate(Screens.Settings.route)
                                },
                                onShippingClick = {
                                    navHostController.navigate(Screens.ShippingAddressSetting.route)
                                },
                                onMyOrderClick = {
                                    navHostController.navigate(Screens.MyOrder.route)
                                }
                            )
                        }
                        composable(
                            route = Screens.ProductDetail.routeWithArgs,
                            arguments = Screens.ProductDetail.arguments
                        ) {
                            ProductDetail(viewModel = ProductDetailViewModel(),
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onNavigateToCartClick = {
                                    navHostController.navigate(
                                        Screens.Cart.route
                                    )
                                },
                                onReviewClick = {
                                    navHostController.navigate(
                                        Screens.RatingReview.route
                                    )
                                }
                            )
                        }
                        composable(route = Screens.Cart.route) {
                            CartScreen(
                                onProductClick = {
                                    navHostController.navigate(
                                        Screens.ProductDetail.getRouteWithArg(it)
                                    )
                                },
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onCheckoutClick = {
                                    navHostController.navigate(
                                        Screens.Checkout.route
                                    )
                                }
                            )
                        }
                        composable(route = Screens.Checkout.route) {
                            CheckoutScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onSubmitClick = {
                                    navHostController.navigate(
                                        Screens.SuccessOrder.route
                                    )
                                }
                            )
                        }
                        composable(route = Screens.SuccessOrder.route) {
                            SuccessOrder(
                                onTrackOrdersClick = {
                                    navHostController.navigate(
                                        Graph.MAIN
                                    ) {
                                        popUpTo(Graph.MAIN) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onBackToHomeClick = {
                                    navHostController.navigate(
                                        Graph.MAIN
                                    ) {
                                        popUpTo(Graph.MAIN) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable(route = Screens.RatingReview.route) {
                            RatingReview(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onSubmitClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.MyReview.route) {
                            MyReview(
                                onBackClicked = {
                                    navHostController.popBackStack()
                                },
                                onSubmitClick = {
                                    navHostController.navigate(Screens.Cart.route)
                                }
                            )
                        }
                        composable(route = Screens.Settings.route) {
                            Settings(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.PaymentMethod.route) {
                            PaymentMethod(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onCardAddClick = {
                                    navHostController.navigate(Screens.AddPaymentMethod.route)
                                }
                            )
                        }
                        composable(route = Screens.AddPaymentMethod.route) {
                            AddPaymentMethod(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.ShippingAddressSetting.route) {
                            ShippingAddressSetting(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onAddressAddClick = {
                                    navHostController.navigate(Screens.AddShippingAddress.route)
                                }
                            )
                        }
                        composable(route = Screens.AddShippingAddress.route) {
                            AddShippingAddress(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.MyOrder.route) {
                            MyOrder(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.Search.route) {
                            Search(
                                onBackClick = {
                                    navHostController.popBackStack()
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