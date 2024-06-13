package com.fjjukic.furniture4you.ui.main.notification

import com.fjjukic.furniture4you.ui.common.model.Message
import com.fjjukic.furniture4you.ui.common.model.NotificationModel

data class NotificationScreenState(
    val notifications: List<NotificationModel>,
    val message: Message? = null
)
