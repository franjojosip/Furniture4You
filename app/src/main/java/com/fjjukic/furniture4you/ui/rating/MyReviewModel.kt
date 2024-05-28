package com.fjjukic.furniture4you.ui.rating

import androidx.annotation.DrawableRes
import java.util.UUID

data class MyReviewModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val price: String,
    val description: String,
    val date: String,
    @DrawableRes
    val imageResId: Int,
    val rating: Double = 5.0
)