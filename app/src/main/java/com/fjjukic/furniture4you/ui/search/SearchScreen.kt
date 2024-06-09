package com.fjjukic.furniture4you.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val query by viewModel.query.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val focused by viewModel.focused.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val searching by viewModel.searching.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val displayType by viewModel.displayType.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.color_white))
        ) {
            FurnitureSearchBar(
                query = query,
                searchFocused = focused,
                searching = searching,
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

            when (val result = displayType) {
                is DisplayType.Categories -> {
                    SearchCategories(result.categories)
                }

                is DisplayType.Suggestions -> {
                    SearchSuggestions(
                        result.suggestions,
                        onSuggestionSelect = { suggestion ->
                            viewModel.onQueryChange(TextFieldValue(suggestion))
                        }
                    )
                }

                is DisplayType.Results -> {
                    SearchResults(
                        result.results,
                        result.filters,
                        onProductClick,
                        onCartClick
                    )
                }

                is DisplayType.NoResults -> NoResults(result.query)
            }
        }
    }
}