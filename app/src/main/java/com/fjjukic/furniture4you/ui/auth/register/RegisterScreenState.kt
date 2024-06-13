package com.fjjukic.furniture4you.ui.auth.register

data class RegisterScreenState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val messageResId: Int? = null,
    val isBiometricsAvailable: Boolean = false,
    val shouldRequestBiometrics: Boolean = false
)