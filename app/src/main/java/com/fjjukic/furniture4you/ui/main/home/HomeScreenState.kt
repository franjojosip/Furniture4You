package com.fjjukic.furniture4you.ui.main.home

import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.Product

data class HomeScreenState(
    val categories: List<CategoryItem>,
    val products: List<Product>,
    val selectedCategory: Int = 0
)