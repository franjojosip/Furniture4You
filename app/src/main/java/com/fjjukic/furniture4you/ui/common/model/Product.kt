package com.fjjukic.furniture4you.ui.common.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: String,
    val title: String,
    val price: String,
    val description: String,
    val imageUrl: String,
    @DrawableRes
    val imageResId: Int,
)