package com.fjjukic.furniture4you.ui.setting

sealed class NotificationGroup {
    data class Sales(val state: Boolean) : NotificationGroup()
    data class NewArrivals(val state: Boolean) : NotificationGroup()
    data class DeliveryStatusChanges(val state: Boolean) : NotificationGroup()
}
