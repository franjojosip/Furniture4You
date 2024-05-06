package com.fjjukic.furniture4you.ui.profile

data class SettingsViewState(
    val name: String,
    val email: String,
    val password: String,
    val salesState: Boolean,
    val newArrivalsState: Boolean,
    val deliveryStatusChangeState: Boolean,
)