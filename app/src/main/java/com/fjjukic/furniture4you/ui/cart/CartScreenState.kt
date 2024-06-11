package com.fjjukic.furniture4you.ui.cart

import com.fjjukic.furniture4you.ui.common.model.CartProduct
import com.fjjukic.furniture4you.ui.common.model.Message

data class CartScreenState(
    val products: List<CartProduct>,
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val promoCode: String = "",
    val message: Message? = null
)