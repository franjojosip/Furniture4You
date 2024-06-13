package com.fjjukic.furniture4you.ui.auth.login

import javax.crypto.Cipher

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isBiometricAvailable: Boolean = false,
    val biometricsPromptData: BiometricData? = null,
    val messageResId: Int? = null
)

data class BiometricData(
    val token: String,
    val cipher: Cipher
)