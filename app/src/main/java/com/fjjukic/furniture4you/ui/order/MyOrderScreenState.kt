package com.fjjukic.furniture4you.ui.order

import com.fjjukic.furniture4you.ui.common.model.Order

data class MyOrderState(
    val tabs: List<OrderStatus>,
    val orders: List<Order>
)

sealed class OrderStatus(val name: String) {
    data object Delivered : OrderStatus("Delivered")
    data object Processing : OrderStatus("Processing")
    data object Canceled : OrderStatus("Canceled")
}
