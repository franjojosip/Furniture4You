package com.fjjukic.furniture4you.ui.auth.login

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (!ValidationUtils.isEmailValid(email) || !ValidationUtils.isPasswordValid(password)) {
            _state.update { it.copy(messageResId = R.string.error_check_fields) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            val result = mainRepository.login(email, password)
            _state.update { it.copy(isLoading = false) }

            if (result == AuthenticationState.AUTHENTICATED) {
                _state.update { it.copy(isAuthenticated = true) }
            } else {
                _state.update { it.copy(messageResId = R.string.error_invalid_credentials) }
            }
        }
    }

    fun setupLockWithBiometrics(isLocked: Boolean) {
        mainRepository.setupLockWithBiometrics(isLocked)
    }

    fun checkIfAppLockedWithBiometrics(): Boolean {
        return mainRepository.checkIfAppLockedWithBiometrics()
    }
}


object BiometricsHelper {

    fun checkIfBiometricsAvailable(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
    }

    data class BiometricPromptModel(
        val title: Int,
        val cancelBtnText: Int
    )

    fun showPrompt(
        activity: FragmentActivity,
        prompt: BiometricPromptModel,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit,
    ) {
        val biometricPrompt =
            BiometricPrompt(activity, object : BiometricPrompt.AuthenticationCallback() {
                // Called when an unrecoverable error has been encountered and authentication has stopped.
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError.invoke("ERROR")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError.invoke("AUTH FAILED")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("title")
            .setNegativeButtonText("cancelBtnText")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}