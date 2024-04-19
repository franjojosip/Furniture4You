package com.fjjukic.furniture4you.ui.favorite

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun FavoritePreview() {
    Favorite({}, {})
}

@Composable
fun Favorite(
    onProductClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val todoToast = Toast.makeText(
        LocalContext.current,
        stringResource(id = R.string.new_feature_message),
        Toast.LENGTH_SHORT
    )

    Scaffold(
        modifier,
        topBar = {
        },
        bottomBar = {
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .height(60.dp)
                    .fillMaxWidth(),
                onClick = {
                    todoToast.show()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.btn_add_all_to_my_cart),
                    style = GelatioTypography.bodyMedium,
                    color = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(
        title = "My cart",
        startIconResId = R.drawable.ic_search,
        endIconResId = R.drawable.ic_favorite,
        onStartActionClick = { /*TODO*/ },
        onEndActionClick = { /*TODO*/ })
}

@Composable
fun Header(
    title: String,
    startIconResId: Int,
    endIconResId: Int,
    onStartActionClick: () -> Unit,
    onEndActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        IconButton(onClick = onStartActionClick) {
            Icon(
                painter = painterResource(id = startIconResId),
                contentDescription = stringResource(R.string.content_desc_visibility_icon)
            )
        }
        Text(
            text = title,
            style = NunitoSansTypography.titleSmall,
            color = colorResource(id = R.color.medium_gray),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = onEndActionClick) {
            Icon(
                painter = painterResource(id = endIconResId),
                contentDescription = stringResource(R.string.content_desc_visibility_icon)
            )
        }
    }
}