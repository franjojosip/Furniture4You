package com.fjjukic.furniture4you.ui.order

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        MyOrderState(
            tabs = listOf(OrderStatus.Delivered, OrderStatus.Processing, OrderStatus.Canceled),
            orders = MockRepository.getOrders()
        )
    )
    val state = _state.asStateFlow()

    val deliveredOrders get() = _state.value.orders.filter { it.status == OrderStatus.Delivered }
    val processedOrders = _state.value.orders.filter { it.status == OrderStatus.Processing }
    val canceledOrders = _state.value.orders.filter { it.status == OrderStatus.Canceled }

}