package com.fjjukic.furniture4you.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.DisplayType
import com.fjjukic.furniture4you.ui.common.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val products = MockRepository.getProducts()
    private var searchResults = listOf<Product>()

    val query = MutableStateFlow(TextFieldValue(""))
    val focused = MutableStateFlow(false)
    val searching = MutableStateFlow(false)

    private val filters by lazy { MockRepository.getCategories() }
    private val searchSuggestions by lazy { MockRepository.getSearchSuggestions() }
    private val searchCategoryCollections by lazy { MockRepository.getSearchCategoryCollections() }

    private val _displayType = MutableStateFlow<DisplayType>(
        DisplayType.Categories(categories = MockRepository.getSearchCategoryCollections())
    )
    val displayType: StateFlow<DisplayType> = _displayType

    suspend fun search(value: String) = withContext(Dispatchers.Default) {
        searching.value = true
        searchResults = products.filter { it.title.contains(value, ignoreCase = true) }
        delay(300L) // simulate an I/O delay
        searching.value = false

        refreshDisplayType()
    }

    private fun refreshDisplayType() {
        _displayType.value = when {
            !focused.value && query.value.text.isEmpty() -> DisplayType.Categories(categories = searchCategoryCollections)
            focused.value && query.value.text.isEmpty() -> DisplayType.Suggestions(suggestions = searchSuggestions)
            searchResults.isEmpty() -> DisplayType.NoResults(query.value.text)
            else -> DisplayType.Results(filters, searchResults)
        }
    }

    fun onQueryChange(value: TextFieldValue) {
        viewModelScope.launch(Dispatchers.IO) {
            query.value = value
            search(value.text)
        }
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