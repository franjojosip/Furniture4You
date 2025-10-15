package com.fjjukic.furniture4you.ui.common.repository

import androidx.biometric.BiometricPrompt
import com.fjjukic.furniture4you.ui.auth.login.BiometricData


class FakeMainRepository : MainRepository {
    var logoutCalled = false
    override fun getShowPrelogin(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPreloginShown() {
        TODO("Not yet implemented")
    }

    override fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): AuthenticationState {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthenticationState {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        logoutCalled = true
    }

    override fun isBiometricsAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkBiometricsAvailable(): BiometricsAvailability {
        TODO("Not yet implemented")
    }

    override fun setupLockWithBiometrics(
        isLocked: Boolean,
        authResult: BiometricPrompt.AuthenticationResult
    ): AuthenticationState {
        TODO("Not yet implemented")
    }

    override fun checkIfAppLockedWithBiometrics(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onBiometricAuthenticationSuccess() {
        TODO("Not yet implemented")
    }

    override fun getBiometricsPromptData(): BiometricData {
        TODO("Not yet implemented")
    }

    override fun hasEncryptedData(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBiometricData(): BiometricData {
        TODO("Not yet implemented")
    }

    override fun saveBiometricsResult(authResult: BiometricPrompt.AuthenticationResult) {
        TODO("Not yet implemented")
    }

    override fun disableBiometrics(): Boolean {
        TODO("Not yet implemented")
    }
}