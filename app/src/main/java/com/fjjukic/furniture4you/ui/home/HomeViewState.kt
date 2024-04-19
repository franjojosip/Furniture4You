package com.fjjukic.furniture4you.ui.home

import com.fjjukic.furniture4you.ui.model.Product

data class HomeViewState(
    val categories: List<CategoryItemModel>,
    val products: List<Product>
)