package com.fjjukic.furniture4you.ui.home

import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.Product

data class HomeViewState(
    val categories: List<CategoryItem>,
    val products: List<Product>
)