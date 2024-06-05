package com.fjjukic.furniture4you.ui.common.model

sealed class DisplayType {
    data class Categories(
        val categories: List<SearchCategoryCollection>
    ) : DisplayType()

    data class Suggestions(
        val suggestions: List<SearchSuggestionGroup>
    ) : DisplayType()

    data class Results(
        val filters: List<CategoryItem>,
        val results: List<Product>
    ) : DisplayType()

    data class NoResults(
        val query: String
    ) : DisplayType()
}
