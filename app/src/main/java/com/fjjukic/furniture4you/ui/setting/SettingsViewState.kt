package com.fjjukic.furniture4you.ui.setting

data class SettingsViewState(
    val password: String,
    val personalInformation: PersonalInformation,
    val salesState: Boolean,
    val newArrivalsState: Boolean,
    val deliveryStatusChangeState: Boolean,
)

data class PersonalInformation(
    val name: String,
    val email: String
)