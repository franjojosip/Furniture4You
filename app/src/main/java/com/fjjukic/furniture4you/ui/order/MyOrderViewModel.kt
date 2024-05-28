package com.fjjukic.furniture4you.ui.order

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor() : ViewModel() {

    sealed class OrderStatus(val name: String) {
        data object Delivered : OrderStatus("Delivered")
        data object Processing : OrderStatus("Processing")
        data object Canceled : OrderStatus("Canceled")
    }

    val tabs = listOf(OrderStatus.Delivered, OrderStatus.Processing, OrderStatus.Canceled)

    private val orders = MockRepository.getOrders()
    val deliveredOrders = orders.filter { it.status == OrderStatus.Delivered }
    val processedOrders = orders.filter { it.status == OrderStatus.Processing }
    val canceledOrders = orders.filter { it.status == OrderStatus.Canceled }

}