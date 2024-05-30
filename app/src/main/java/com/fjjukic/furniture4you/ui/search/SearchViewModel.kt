package com.fjjukic.furniture4you.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    enum class DisplayType {
        Categories, Suggestions, Results, NoResults
    }

    private val products = MockRepository.getProducts()

    var searchResults = MutableStateFlow(products)

    var query = MutableStateFlow(TextFieldValue(""))
    var focused = MutableStateFlow(false)
    var searching = MutableStateFlow(false)

    val displayType = MutableStateFlow(DisplayType.Categories)

    fun search(value: String) {
        searching.value = true
        searchResults.value = products.filter { it.title.contains(value, ignoreCase = true) }
        searching.value = false
        refreshDisplayType()
    }

    private fun refreshDisplayType() {
        displayType.value = when {
            !focused.value && query.value.text.isEmpty() -> DisplayType.Categories
            focused.value && query.value.text.isEmpty() -> DisplayType.Suggestions
            searchResults.value.isEmpty() -> DisplayType.NoResults
            else -> DisplayType.Results
        }
    }

    fun onQueryChange(value: TextFieldValue) {
        query.value = value
        refreshDisplayType()
    }

    fun onSearchFocusChange(value: Boolean) {
        focused.value = value
        refreshDisplayType()
    }

    fun onClearQuery() {
        query.value = TextFieldValue("")
        refreshDisplayType()
    }

}