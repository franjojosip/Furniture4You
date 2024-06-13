package com.fjjukic.furniture4you.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MockRepository.getSettingsViewState())
    val state = _state.asStateFlow()

    fun onLogoutClick() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.logout()
            _state.update {
                it.copy(isLoggedOut = true)
            }
        }
    }
}