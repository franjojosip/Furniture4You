package com.fjjukic.furniture4you.ui.common.model

import androidx.compose.runtime.Immutable

@Immutable
data class Message(
    val toastResId: Int? = null,
    val snackbarResId: Int? = null,
    val snackbarActionResId: Int? = null
)
