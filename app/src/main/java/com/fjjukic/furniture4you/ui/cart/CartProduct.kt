package com.fjjukic.furniture4you.ui.cart

import com.fjjukic.furniture4you.ui.model.Product

data class CartProduct(
    val product: Product,
    var count: Int = 1
)