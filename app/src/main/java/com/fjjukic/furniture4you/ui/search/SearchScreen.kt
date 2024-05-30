package com.fjjukic.furniture4you.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.components.FurnitureSearchBar
import ht.ferit.fjjukic.foodlovers.R

@Preview("default", showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val focused by viewModel.focused.collectAsStateWithLifecycle()
    val searching by viewModel.searching.collectAsStateWithLifecycle()

    val searchResults = viewModel.searchResults.collectAsStateWithLifecycle()
    val displayType = viewModel.displayType.collectAsStateWithLifecycle()

    LaunchedEffect(query.text) {
        viewModel.search(query.text)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
        }
    }
}