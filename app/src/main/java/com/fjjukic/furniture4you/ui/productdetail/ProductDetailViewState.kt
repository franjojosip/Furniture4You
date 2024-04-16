package com.fjjukic.furniture4you.ui.productdetail

data class ProductDetailViewState(
    val selectedProductDetail: ProductDetail,
    val counter: Int = 1
)

class ProductDetail(
    val title: String,
    val imageUrl: String,
    val description: String,
    val price: Double = 0.0,
    val rating: Double = 5.0,
    val reviews: Int = 0
)