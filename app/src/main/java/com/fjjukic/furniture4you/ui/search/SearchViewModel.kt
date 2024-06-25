package com.fjjukic.furniture4you.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.DisplayType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MockRepository.getSearchScreenState())
    val state = _state.asStateFlow()

    private fun search(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSearching = true) }
            delay(300L) // simulate an I/O delay
            val results =
                _state.value.products.filter { it.title.contains(value, ignoreCase = true) }

            _state.update {
                it.copy(
                    searchResults = results,
                    isSearching = false,
                    displayType = getDisplayType(it)
                )
            }
        }
    }

    private fun getDisplayType(
        searchState: SearchScreenState,
    ): DisplayType {
        return when {
            !searchState.isFocused && searchState.query.text.isEmpty() -> DisplayType.Categories(
                categories = searchState.searchCategoryCollections
            )

            searchState.isFocused && searchState.query.text.isEmpty() -> DisplayType.Suggestions(
                suggestions = searchState.searchSuggestions
            )

            searchState.searchResults.isEmpty() -> DisplayType.NoResults(searchState.query.text)
            else -> DisplayType.Results(searchState.filters, searchState.searchResults)
        }
    }

    fun onQueryChange(value: TextFieldValue) {
        _state.update { it.copy(query = value) }
        search(value.text)
    }

    fun onSearchFocusChange(value: Boolean) {
        _state.update { it.copy(isFocused = value, displayType = getDisplayType(it)) }
    }

    fun onClearQuery() {
        _state.update { it.copy(query = TextFieldValue(""), displayType = getDisplayType(it)) }
    }

}