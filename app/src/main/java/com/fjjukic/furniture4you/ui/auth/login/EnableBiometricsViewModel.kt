package com.fjjukic.furniture4you.ui.auth.login

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.BiometricsAvailability
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class EnableBiometricsScreenState(
    val biometricsAvailability: BiometricsAvailability,
    val biometricsActivated: Boolean? = null,
    val messageResId: Int? = null
)

@HiltViewModel
class EnableBiometricsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<EnableBiometricsScreenState> = MutableStateFlow(
        EnableBiometricsScreenState(
            biometricsAvailability = mainRepository.checkBiometricsAvailable(),
        )
    )
    val state = _state.asStateFlow()

    fun onBiometricActivationSuccess() {
        mainRepository.setupLockWithBiometrics(true)
        _state.update {
            it.copy(biometricsActivated = true)
        }
    }

    fun onBiometricActivationFailed() {
        _state.update {
            it.copy(messageResId = R.string.error_biometrics_activation_failed)
        }
    }
}