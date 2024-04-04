package com.fjjukic.furniture4you.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.ErrorColor
import ht.ferit.fjjukic.foodlovers.R

@Composable
fun ErrorField(errorMessage: String?) {
    Text(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp),
        text = errorMessage ?: stringResource(R.string.error_invalid_field),
        fontSize = 12.sp,
        color = ErrorColor
    )
}