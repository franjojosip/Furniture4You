package com.fjjukic.furniture4you.ui.common.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class NotificationModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val hideImage: Boolean = false,
    val imageResId: Int? = null,
    val tag: NotificationTag? = null
)