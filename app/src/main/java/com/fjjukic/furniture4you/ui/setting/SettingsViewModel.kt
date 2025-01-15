package com.fjjukic.furniture4you.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MockRepository.getSettingsViewState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                biometricsAvailable = repository.isBiometricsAvailable(),
                biometricsEnabledState = repository.checkIfAppLockedWithBiometrics()
            )
        }
    }

    fun onSwitchStateChange(group: NotificationGroup) {
        _state.update {
            when (group) {
                is NotificationGroup.Sales -> it.copy(salesState = group.state)
                is NotificationGroup.NewArrivals -> it.copy(newArrivalsState = group.state)
                is NotificationGroup.DeliveryStatusChanges -> it.copy(deliveryStatusChangeState = group.state)
            }
        }
    }

    fun onBiometricsSuccess() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.onBiometricAuthenticationSuccess()
            _state.update {
                it.copy(
                    showBiometricsPrompt = false,
                    biometricsEnabledState = true,
                    messageId = R.string.label_biometrics_enabled
                )
            }
        }
    }

    fun onBiometricsSkip() {
        _state.update {
            it.copy(
                showBiometricsPrompt = false,
                biometricsEnabledState = false
            )
        }
    }

    fun onBiometricsClick(isEnabled: Boolean) {
        if (repository.isBiometricsAvailable() && isEnabled) {
            _state.update {
                it.copy(
                    showBiometricsPrompt = true,
                    biometricsEnabledState = false
                )
            }
        } else if (repository.isBiometricsAvailable() && !isEnabled) {
            if (repository.disableBiometrics()) {
                _state.update {
                    it.copy(
                        showBiometricsPrompt = false,
                        biometricsEnabledState = false,
                        messageId = R.string.label_biometrics_disabled
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        showBiometricsPrompt = false,
                        biometricsEnabledState = false,
                        messageId = R.string.label_biometrics_disabled_fail
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    showBiometricsPrompt = false,
                    biometricsEnabledState = false,
                    messageId = R.string.error_biometrics_not_available
                )
            }
        }
    }

    fun onEditClick(personalInformation: PersonalInformation) {
        _state.update {
            it.copy(personalInformation = personalInformation)
        }
    }

    fun onPasswordChange(value: String) {
        _state.update {
            it.copy(password = value.replace(Regex("\\S"), "*"))
        }
    }

    fun onMessageShown() {
        _state.update {
            it.copy(messageId = null)
        }
    }
}