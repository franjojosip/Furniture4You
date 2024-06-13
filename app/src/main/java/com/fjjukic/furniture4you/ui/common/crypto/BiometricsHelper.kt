package com.fjjukic.furniture4you.ui.common.crypto

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.fjjukic.furniture4you.R
import javax.crypto.Cipher


object BiometricsHelper {

    fun checkIfBiometricsAvailable(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
    }

    fun registerUserBiometrics(
        context: FragmentActivity,
        cipher: Cipher,
        onSuccess: (authResult: BiometricPrompt.AuthenticationResult) -> Unit = {}
    ) {
        val biometricPrompt = getBiometricPrompt(context, onSuccess)

        biometricPrompt.authenticate(
            getPromptInfo(context),
            BiometricPrompt.CryptoObject(cipher)
        )
    }

    // Authenticate user using biometrics by decrypting stored token
    fun authenticateUser(
        cipher: Cipher,
        context: FragmentActivity,
        onSuccess: () -> Unit
    ) {
        val promptInfo = getPromptInfo(context)
        val biometricPrompt = getBiometricPrompt(context) { _ ->
            onSuccess()
        }

        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    private fun getBiometricPrompt(
        context: FragmentActivity,
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        return BiometricPrompt(
            context,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess(result)
                }
            }
        )
    }

    private fun getPromptInfo(context: FragmentActivity): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.label_use_biometrics))
            .setNegativeButtonText(context.getString(R.string.btn_cancel))
            .setConfirmationRequired(false)
            .build()
    }
}