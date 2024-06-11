package com.fjjukic.furniture4you.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.DisplayType
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.common.model.SearchCategoryCollection
import com.fjjukic.furniture4you.ui.common.model.SearchSuggestionGroup

data class SearchScreenState(
    val products: List<Product>,
    val filters: List<CategoryItem>,
    val searchSuggestions: List<SearchSuggestionGroup>,
    val searchCategoryCollections: List<SearchCategoryCollection>,
    val displayType: DisplayType,
    val searchResults: List<Product> = listOf(),
    val query: TextFieldValue = TextFieldValue(""),
    val isFocused: Boolean = false,
    val isSearching: Boolean = false,
)