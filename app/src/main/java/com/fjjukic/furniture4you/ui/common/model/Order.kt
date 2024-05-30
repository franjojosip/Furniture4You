package com.fjjukic.furniture4you.ui.common.model

import com.fjjukic.furniture4you.ui.order.MyOrderViewModel
import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val number: String,
    val date: String,
    val quantity: Int,
    val amount: Double,
    val status: MyOrderViewModel.OrderStatus
)