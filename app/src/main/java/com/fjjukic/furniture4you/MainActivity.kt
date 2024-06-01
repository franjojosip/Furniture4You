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
import com.fjjukic.furniture4you.ui.main.setting.SettingsScreen
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import com.fjjukic.furniture4you.ui.navigation.authNavigationGraph
import com.fjjukic.furniture4you.ui.order.MyOrderScreen
import com.fjjukic.furniture4you.ui.order.SuccessOrderScreen
import com.fjjukic.furniture4you.ui.payment.PaymentMethodAddScreen
import com.fjjukic.furniture4you.ui.payment.PaymentMethodScreen
import com.fjjukic.furniture4you.ui.productdetail.ProductDetailScreen
import com.fjjukic.furniture4you.ui.productdetail.ProductDetailViewModel
import com.fjjukic.furniture4you.ui.rating.RatingReviewScreen
import com.fjjukic.furniture4you.ui.review.MyReviewScreen
import com.fjjukic.furniture4you.ui.search.SearchScreen
import com.fjjukic.furniture4you.ui.shipping.AddShippingAddressScreen
import com.fjjukic.furniture4you.ui.shipping.ShippingAddressScreen
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
                                onLogoutClick = {
                                    /* TODO */
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
                                    navHostController.navigate(Screens.ShippingAddress.route)
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
                            ProductDetailScreen(
                                viewModel = ProductDetailViewModel(),
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
                            SuccessOrderScreen(
                                onTrackOrdersClick = {
                                    /* TODO */
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
                            RatingReviewScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onSubmitClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.MyReview.route) {
                            MyReviewScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onSubmitClick = {
                                    navHostController.navigate(Screens.Cart.route)
                                }
                            )
                        }
                        composable(route = Screens.Settings.route) {
                            SettingsScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.PaymentMethod.route) {
                            PaymentMethodScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onCardAddClick = {
                                    navHostController.navigate(Screens.PaymentMethodAdd.route)
                                }
                            )
                        }
                        composable(route = Screens.PaymentMethodAdd.route) {
                            PaymentMethodAddScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.ShippingAddress.route) {
                            ShippingAddressScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                },
                                onAddressAddClick = {
                                    navHostController.navigate(Screens.ShippingAddressAdd.route)
                                }
                            )
                        }
                        composable(route = Screens.ShippingAddressAdd.route) {
                            AddShippingAddressScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.MyOrder.route) {
                            MyOrderScreen(
                                onBackClick = {
                                    navHostController.popBackStack()
                                }
                            )
                        }
                        composable(route = Screens.Search.route) {
                            SearchScreen(
                                onProductClick = {
                                    navHostController.navigate(
                                        Screens.ProductDetail.getRouteWithArg(it)
                                    )
                                },
                                onCartClick = {
                                    navHostController.navigate(Screens.Cart.route)
                                },
                            )
                        }
                        authNavigationGraph(navHostController)
                    }
                }
            }
        }
    }
}