package com.fjjukic.furniture4you.ui.auth.enable_biometrics

import com.fjjukic.furniture4you.ui.auth.login.BiometricData
import com.fjjukic.furniture4you.ui.common.repository.BiometricsAvailability

data class EnableBiometricsScreenState(
    val biometricsAvailability: BiometricsAvailability,
    val biometricsActivated: Boolean? = null,
    val biometricsFailed: Boolean? = null,
    val biometricData: BiometricData? = null,
    val messageResId: Int? = null
)