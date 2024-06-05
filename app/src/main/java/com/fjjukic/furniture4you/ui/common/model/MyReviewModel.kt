package com.fjjukic.furniture4you.ui.common.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
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