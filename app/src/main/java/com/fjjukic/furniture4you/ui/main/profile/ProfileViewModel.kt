package com.fjjukic.furniture4you.ui.main.profile

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.main.setting.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MockRepository.getSettingsViewState())
    val uiState: StateFlow<SettingsViewState> = _uiState
}