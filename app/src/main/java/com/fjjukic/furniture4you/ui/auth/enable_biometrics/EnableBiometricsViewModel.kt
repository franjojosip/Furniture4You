package com.fjjukic.furniture4you.ui.auth.enable_biometrics

import androidx.biometric.BiometricPrompt
import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EnableBiometricsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<EnableBiometricsScreenState> = MutableStateFlow(
        EnableBiometricsScreenState(
            biometricsAvailability = mainRepository.checkBiometricsAvailable(),
            biometricData = mainRepository.getBiometricData()
        )
    )
    val state = _state.asStateFlow()

    fun onBiometricActivationSuccess(authResult: BiometricPrompt.AuthenticationResult) {
            val result = mainRepository.setupLockWithBiometrics(true, authResult)
            if (result == AuthenticationState.VALID_BIOMETRY) {
                _state.update {
                    it.copy(biometricsActivated = true)
                }
            } else {
                _state.update {
                    it.copy(
                        messageResId = R.string.error_biometrics_activation_failed,
                        biometricsFailed = true,
                    )
                }
            }
    }
}