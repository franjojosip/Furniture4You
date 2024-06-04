package com.fjjukic.furniture4you.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fjjukic.furniture4you.ui.cart.CartScreen
import com.fjjukic.furniture4you.ui.checkout.CheckoutScreen
import com.fjjukic.furniture4you.ui.common.viewmodel.MainViewModel
import com.fjjukic.furniture4you.ui.home.HomeScreen
import com.fjjukic.furniture4you.ui.main.favorite.FavoriteScreen
import com.fjjukic.furniture4you.ui.main.notification.NotificationScreen
import com.fjjukic.furniture4you.ui.main.profile.ProfileScreen
import com.fjjukic.furniture4you.ui.main.setting.SettingsScreen
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

fun NavGraphBuilder.homeGraph(
    navHostController: NavHostController,
    onNavigateToBottomBarRoute: (String) -> Unit
) {
    navigation(
        route = Graph.HOME,
        startDestination = Screens.HomeSections.Home.route
    ) {
        composable(Screens.HomeSections.Home.route) {
            HomeScreen(
                onProductClick = {
                    navHostController.navigate(
                        Screens.ProductDetail.getRouteWithArg(it)
                    )
                },
                onCartClick = {
                    navHostController.navigate(Screens.Cart.route)
                },
                onSearchClick = {
                    navHostController.navigate(Screens.Search.route)
                },
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
        composable(Screens.HomeSections.Favorites.route) {
            FavoriteScreen(
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
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
        composable(Screens.HomeSections.Notification.route) {
            NotificationScreen(
                onSearchClick = {
                    navHostController.navigate(Screens.Search.route)
                },
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute
            )
        }
        composable(Screens.HomeSections.Profile.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            ProfileScreen(
                onLogoutClick = {
                    viewModel.logout().also {
                        navHostController.navigate(Graph.AUTH) {
                            popUpTo(navHostController.graph.id)
                        }
                    }
                },
                onNavigateToBottomBarRoute = onNavigateToBottomBarRoute,
                onNavigateToRoute = navHostController::navigate
            )
        }
    }
    addProfileSections(navHostController)

    composable(
        route = Screens.ProductDetail.routeWithArgs,
        arguments = Screens.ProductDetail.arguments
    ) {
        ProductDetailScreen(
            viewModel = ProductDetailViewModel(),
            onNavigateToCartClick = {
                navHostController.navigate(
                    Screens.Cart.route
                )
            },
            onReviewClick = {
                navHostController.navigate(
                    Screens.RatingReview.route
                )
            },
            onBackClick = navHostController::navigateUp
        )
    }
    composable(route = Screens.Cart.route) {
        CartScreen(
            onProductClick = {
                navHostController.navigate(
                    Screens.ProductDetail.getRouteWithArg(it)
                )
            },
            onCheckoutClick = {
                navHostController.navigate(
                    Screens.Checkout.route
                )
            },
            onBackClick = navHostController::navigateUp
        )
    }
    composable(route = Screens.Checkout.route) {
        CheckoutScreen(
            onSubmitClick = {
                navHostController.navigate(
                    Screens.SuccessOrder.route
                )
            },
            onBackClick = navHostController::navigateUp
        )
    }
    composable(route = Screens.SuccessOrder.route) {
        SuccessOrderScreen(
            onTrackOrdersClick = {
                navHostController.popBackStack(Screens.HomeSections.Home.route, inclusive = false)
                onNavigateToBottomBarRoute(Screens.HomeSections.Profile.route)
                navHostController.navigate(Screens.ProfileSections.MyOrders.route)
            },
            onBackToHomeClick = {
                navHostController.popBackStack(Screens.HomeSections.Home.route, inclusive = false)
            }
        )
    }
    composable(route = Screens.RatingReview.route) {
        RatingReviewScreen(navHostController::navigateUp)
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
}

fun NavGraphBuilder.addProfileSections(navHostController: NavHostController) {
    composable(route = Screens.ProfileSections.MyOrders.route) {
        MyOrderScreen(navHostController::navigateUp)
    }

    composable(route = Screens.ProfileSections.ShippingAddress.route) {
        ShippingAddressScreen(
            onAddressAddClick = {
                navHostController.navigate(Screens.ShippingAddressAdd.route)
            },
            onBackClick = navHostController::navigateUp
        )
    }
    composable(route = Screens.ShippingAddressAdd.route) {
        AddShippingAddressScreen(navHostController::navigateUp)
    }

    composable(route = Screens.ProfileSections.PaymentMethod.route) {
        PaymentMethodScreen(
            onCardAddClick = {
                navHostController.navigate(Screens.PaymentMethodAdd.route)
            },
            onBackClick = navHostController::navigateUp
        )
    }
    composable(route = Screens.PaymentMethodAdd.route) {
        PaymentMethodAddScreen(navHostController::navigateUp)
    }

    composable(route = Screens.ProfileSections.MyReviews.route) {
        MyReviewScreen(
            onSubmitClick = {
                navHostController.navigate(Screens.Cart.route)
            },
            onBackClick = navHostController::navigateUp
        )
    }

    composable(route = Screens.ProfileSections.Settings.route) {
        SettingsScreen(navHostController::navigateUp)
    }
}