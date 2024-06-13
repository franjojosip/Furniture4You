package com.fjjukic.furniture4you.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.model.DisplayType
import com.fjjukic.furniture4you.ui.components.FurnitureSearchBar
import com.fjjukic.furniture4you.ui.components.NoResults
import com.fjjukic.furniture4you.ui.components.SearchCategories
import com.fjjukic.furniture4you.ui.components.SearchResults
import com.fjjukic.furniture4you.ui.components.SearchSuggestions

@Preview("default", showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(onProductClick = {}, onCartClick = {})
}

@Composable
fun SearchScreen(
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.color_white))
        ) {
            FurnitureSearchBar(
                query = state.query,
                searchFocused = state.isFocused,
                searching = state.isSearching,
                onQueryChange = {
                    viewModel.onQueryChange(it)
                },
                onSearchFocusChange = {
                    viewModel.onSearchFocusChange(it)
                },
                onClearQuery = {
                    viewModel.onClearQuery()
                },
                modifier = Modifier.padding(top = 12.dp)
            )

            when (state.displayType) {
                is DisplayType.Categories -> {
                    SearchCategories(state.displayType.categories)
                }

                is DisplayType.Suggestions -> {
                    SearchSuggestions(
                        state.displayType.suggestions,
                        onSuggestionSelect = { suggestion ->
                            viewModel.onQueryChange(TextFieldValue(suggestion))
                        }
                    )
                }

                is DisplayType.Results -> {
                    SearchResults(
                        state.displayType.results,
                        state.displayType.filters,
                        onProductClick,
                        onCartClick
                    )
                }

                is DisplayType.NoResults -> NoResults(state.displayType.query)
            }
        }
    }
}