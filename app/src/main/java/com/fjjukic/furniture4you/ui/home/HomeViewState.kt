package com.fjjukic.furniture4you.ui.home

import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.home.model.CategoryItem

data class HomeViewState(
    val categories: List<CategoryItem>,
    val products: List<Product>
)