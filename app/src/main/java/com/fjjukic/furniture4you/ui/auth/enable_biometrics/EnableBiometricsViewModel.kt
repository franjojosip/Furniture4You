package com.fjjukic.furniture4you.ui.auth.enable_biometrics

import androidx.biometric.BiometricPrompt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnableBiometricsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<EnableBiometricsScreenState> = MutableStateFlow(
        EnableBiometricsScreenState(
            biometricsAvailability = mainRepository.checkBiometricsAvailable(),
            biometricsActivated = mainRepository.checkIfAppLockedWithBiometrics(),
            biometricData = mainRepository.getBiometricData()
        )
    )
    val state = _state.asStateFlow()

    fun onBiometricActivationSuccess(authResult: BiometricPrompt.AuthenticationResult) {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun onMessageShown() {
        _state.update {
            it.copy(
                messageResId = null,
            )
        }
    }

    fun resetState() {
        _state.update {
            EnableBiometricsScreenState(
                biometricsAvailability = mainRepository.checkBiometricsAvailable(),
                biometricsActivated = mainRepository.checkIfAppLockedWithBiometrics(),
                biometricData = mainRepository.getBiometricData()
            )
        }
    }
}