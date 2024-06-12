package com.fjjukic.furniture4you.ui.auth.login

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.BiometricsAvailability
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class EnableBiometricsScreenState(
    val biometricsAvailability: BiometricsAvailability,
    val biometricPromptModel: BiometricsHelper.BiometricPromptModel
)

@HiltViewModel
class EnableBiometricsViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<EnableBiometricsScreenState> = MutableStateFlow(
        EnableBiometricsScreenState(
            biometricsAvailability = mainRepository.checkBiometricsAvailable(),
            biometricPromptModel = BiometricsHelper.BiometricPromptModel(
                R.string.label_setup_biometrics,
                R.string.btn_cancel
            )
        )
    )
    val state = _state.asStateFlow()
}