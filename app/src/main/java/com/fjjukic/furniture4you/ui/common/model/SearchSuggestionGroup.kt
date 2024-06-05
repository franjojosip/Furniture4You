package com.fjjukic.furniture4you.ui.common.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchSuggestionGroup(
    val id: Long,
    val name: String,
    val suggestions: List<String>
)