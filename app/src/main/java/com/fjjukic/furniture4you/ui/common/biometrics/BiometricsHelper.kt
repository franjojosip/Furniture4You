package com.fjjukic.furniture4you.ui.common.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.fjjukic.furniture4you.R


object BiometricsHelper {

    fun checkIfBiometricsAvailable(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
    }

    data class BiometricPromptModel(
        val titleResId: Int,
        val cancelBtnTextResId: Int
    )

    fun showPrompt(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        val biometricPrompt =
            BiometricPrompt(activity, object : BiometricPrompt.AuthenticationCallback() {
                // Called when an unrecoverable error has been encountered and authentication has stopped.
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.getString(R.string.label_use_biometrics))
            .setNegativeButtonText(activity.getString(R.string.btn_cancel))
            .setConfirmationRequired(false)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}