package com.fjjukic.furniture4you.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.ui.common.Toolbar
import ht.ferit.fjjukic.foodlovers.R


@Composable
fun Search(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_my_order),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(androidx.compose.ui.graphics.Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .background(androidx.compose.ui.graphics.Color.White)
        ) {
            SearchBar()
        }
    }
}

@Preview("default", showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar()
}

@Composable
private fun SearchBar(
    searchFocused: Boolean = true,
    searching: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        if ("query.text".isEmpty()) {
            SearchHint()
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        ) {
            if (searchFocused) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        tint = colorResource(id = R.color.dark_gray),
                        contentDescription = stringResource(R.string.desc_shipping_addresses)
                    )
                }
            }
            BasicTextField(
                value = "query",
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        //onSearchFocusChange(it.isFocused)
                    }
            )
            if (searching) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.dark_gray),
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .size(24.dp)
                )
            } else {
                Spacer(Modifier.width(36.dp)) // balance arrow icon
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
            tint = colorResource(id = R.color.dark_gray),
            contentDescription = stringResource(R.string.content_desc_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.content_desc_archive),
            color = colorResource(id = R.color.dark_gray),
        )
    }
}