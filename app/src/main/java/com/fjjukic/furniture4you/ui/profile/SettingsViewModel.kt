package com.fjjukic.furniture4you.ui.profile

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class NotificationGroup {
    data class Sales(val state: Boolean) : NotificationGroup()
    data class NewArrivals(val state: Boolean) : NotificationGroup()
    data class DeliveryStatusChanges(val state: Boolean) : NotificationGroup()
}

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MockRepository.getSettingsViewState())
    val uiState: StateFlow<SettingsViewState> = _uiState

    fun onSwitchStateChange(group: NotificationGroup) {
        _uiState.update {
            when (group) {
                is NotificationGroup.Sales -> it.copy(salesState = group.state)
                is NotificationGroup.NewArrivals -> it.copy(newArrivalsState = group.state)
                is NotificationGroup.DeliveryStatusChanges -> it.copy(deliveryStatusChangeState = group.state)
            }
        }
    }
}