package com.fjjukic.furniture4you.ui.common.model

data class CartProduct(
    val product: Product,
    var count: Int = 1
)