package com.fjjukic.furniture4you.ui.common.model

import java.util.UUID

data class NotificationModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val hideImage: Boolean = false,
    val imageResId: Int? = null,
    val tag: NotificationTag? = null
)