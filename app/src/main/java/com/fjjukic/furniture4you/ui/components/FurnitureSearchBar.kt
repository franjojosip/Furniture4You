package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.R


@Preview("default", showBackground = true)
@Composable
fun FurnitureSearchBarPreview() {
    FurnitureSearchBar(
        query = TextFieldValue(""),
        searchFocused = false,
        searching = false,
        onSearchFocusChange = {},
        onClearQuery = {},
        onQueryChange = {}
    )
}

@Preview("search", showBackground = true)
@Composable
fun FurnitureSearchBarFocusedPreview() {
    FurnitureSearchBar(
        query = TextFieldValue(stringResource(id = R.string.placeholder_name)),
        searchFocused = true,
        searching = false,
        onSearchFocusChange = {},
        onClearQuery = {},
        onQueryChange = {}
    )
}

@Composable
fun FurnitureSearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier,
    searchFocused: Boolean = false,
    searching: Boolean = false,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .background(colorResource(id = R.color.color_tinted_white), RoundedCornerShape(24.dp))
    ) {
        if (query.text.isEmpty()) {
            SearchHint()
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        ) {
            if (searchFocused) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        tint = colorResource(id = R.color.color_dark_gray),
                        contentDescription = stringResource(R.string.content_desc_clear_search)
                    )
                }
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        onSearchFocusChange(it.isFocused)
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            if (searching) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.color_dark_gray),
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .size(24.dp)
                )
            } else {
                Spacer(Modifier.width(36.dp))
            }
        }
    }
}

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = colorResource(id = R.color.color_light_gray),
            contentDescription = stringResource(R.string.content_desc_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.label_search),
            color = colorResource(id = R.color.color_light_gray),
        )
    }
}