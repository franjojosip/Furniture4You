package com.fjjukic.furniture4you.ui.setting

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MockRepository.getSettingsViewState())
    val state = _state.asStateFlow()

    fun onSwitchStateChange(group: NotificationGroup) {
        _state.update {
            when (group) {
                is NotificationGroup.Sales -> it.copy(salesState = group.state)
                is NotificationGroup.NewArrivals -> it.copy(newArrivalsState = group.state)
                is NotificationGroup.DeliveryStatusChanges -> it.copy(deliveryStatusChangeState = group.state)
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
}