package com.fjjukic.furniture4you.ui.order

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.ui.common.Toolbar
import com.fjjukic.furniture4you.ui.common.utils.PaymentUtils
import com.fjjukic.furniture4you.ui.mock.MockRepository
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun MyOrderPreview() {
    MyOrder(onBackClick = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyOrder(
    onBackClick: () -> Unit,
    viewModel: MyOrderViewModel = hiltViewModel()
) {
    val pagerState =
        rememberPagerState(initialPage = 0) { viewModel.tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_my_order),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(colorResource(id = R.color.color_white))
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.padding(bottom = 6.dp),
                indicator = { tabPositions ->
                    CustomTabIndicator(position = tabPositions[pagerState.currentPage])
                },
                divider = {}
            ) {
                viewModel.tabs.forEachIndexed { index, tab ->
                    Tab(
                        text = {
                            Text(
                                tab.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.scrollToPage(index)
                                            }
                                        }
                                    ),
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 18.sp
                            )
                        },
                        modifier = Modifier
                            .background(colorResource(id = R.color.color_white))
                            .fillMaxWidth(),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    index,
                                    animationSpec = tween(300)
                                )
                            }
                        },
                        selectedContentColor = colorResource(id = R.color.color_tab_selected),
                        unselectedContentColor = colorResource(id = R.color.color_tab_unselected)
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (viewModel.tabs[page]) {
                    MyOrderViewModel.OrderStatus.Delivered -> {
                        OrderList(viewModel.deliveredOrders)
                    }

                    MyOrderViewModel.OrderStatus.Processing -> {
                        OrderList(viewModel.processedOrders)
                    }

                    MyOrderViewModel.OrderStatus.Canceled -> {
                        OrderList(viewModel.canceledOrders)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderList(orders: List<Order>) {
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.color_white))
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
    ) {
        items(orders) { order ->
            OrderCard(
                order,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun OrderCard(order: Order, modifier: Modifier = Modifier) {
    val statusColor: Color = when (order.status) {
        MyOrderViewModel.OrderStatus.Delivered -> colorResource(id = R.color.color_status_delivered)
        MyOrderViewModel.OrderStatus.Processing -> colorResource(id = R.color.color_status_processing)
        MyOrderViewModel.OrderStatus.Canceled -> colorResource(id = R.color.color_status_canceled)
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 16.dp, top = 16.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.label_order_number, order.number),
                    color = colorResource(id = R.color.color_dark_gray),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = nunitoSansFamily,
                    fontSize = 16.sp
                )
                Text(
                    text = order.date,
                    color = colorResource(id = R.color.color_light_gray),
                    fontFamily = nunitoSansFamily,
                    fontSize = 14.sp
                )
            }
            HorizontalDivider(
                color = colorResource(id = R.color.color_tinted_white),
                thickness = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 16.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DoubleColorText(stringResource(R.string.label_quantity), order.quantity.toString())
                DoubleColorText(
                    stringResource(R.string.label_total_amount),
                    stringResource(
                        id = R.string.title_order_price,
                        PaymentUtils.formatPrice(order.amount)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                        .background(color = colorResource(id = R.color.color_dark_gray))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp),
                        text = stringResource(R.string.label_detail),
                        color = colorResource(id = R.color.color_white),
                        fontFamily = nunitoSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = order.status.name,
                    color = statusColor,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun DoubleColorTextPreview() {
    DoubleColorText(
        startText = stringResource(id = R.string.placeholder_name),
        endText = stringResource(id = R.string.placeholder_name)
    )
}


@Composable
fun DoubleColorText(startText: String, endText: String) {
    Row(modifier = Modifier.wrapContentWidth()) {
        Text(
            text = startText,
            color = colorResource(id = R.color.color_light_gray),
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = endText,
            color = colorResource(id = R.color.color_dark_gray),
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
fun PreviewOrderCard() {
    OrderCard(order = MockRepository.getOrders().first())
}


@Composable
fun CustomTabIndicator(position: TabPosition) {
    return TabRowDefaults.SecondaryIndicator(
        Modifier.customTabIndicatorOffset(position)
    )
}

fun Modifier.customTabIndicatorOffset(currentTabPosition: TabPosition): Modifier = composed {
    val indicatorWidth = 40.dp
    val tabWidth = currentTabPosition.width
    val tabOffset = currentTabPosition.left + (tabWidth - indicatorWidth) / 2

    this
        .fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = tabOffset)
        .width(indicatorWidth)
        .background(
            color = colorResource(id = R.color.color_tab_selected),
            shape = RoundedCornerShape(2.dp)
        )
}