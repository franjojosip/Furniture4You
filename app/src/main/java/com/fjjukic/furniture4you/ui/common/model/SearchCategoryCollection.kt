package com.fjjukic.furniture4you.ui.common.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchCategoryCollection(
    val id: Long,
    val name: String,
    val categories: List<SearchCategory>
)

@Immutable
data class SearchCategory(
    val name: String,
    val imageUrl: String
)