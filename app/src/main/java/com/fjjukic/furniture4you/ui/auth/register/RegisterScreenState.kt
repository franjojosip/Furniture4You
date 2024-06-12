package com.fjjukic.furniture4you.ui.auth.register

data class RegisterScreenState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val messageResId: Int? = null,
    val shouldRequestBiometrics: Boolean? = null
)