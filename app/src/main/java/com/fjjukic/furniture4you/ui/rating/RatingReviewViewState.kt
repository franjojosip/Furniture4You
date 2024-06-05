package com.fjjukic.furniture4you.ui.rating

import com.fjjukic.furniture4you.ui.productdetail.ProductDetail

class RatingReviewViewState(
    val product: ProductDetail,
    val reviews: List<Review>
)

data class Review(
    val name: String,
    val description: String,
    val date: String,
    val imageResId: Int,
    val rating: Double = 5.0
)