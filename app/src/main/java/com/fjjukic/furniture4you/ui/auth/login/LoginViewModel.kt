package com.fjjukic.furniture4you.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(
        LoginScreenState(
            isBiometricAvailable = mainRepository.checkIfAppLockedWithBiometrics(),
        )
    )
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

    fun clearMessage() {
        _state.update { it.copy(messageResId = null) }
    }

    /**
     * Biometry section
     */

    fun onBiometricActionClick() {
        viewModelScope.launch(Dispatchers.IO) {
            if (mainRepository.checkIfAppLockedWithBiometrics()) {
                val biometricsPromptData = mainRepository.getBiometricsPromptData()
                _state.update { it.copy(biometricsPromptData = biometricsPromptData) }
            }
        }
    }

    fun onBiometricLoginSuccess() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.onBiometricAuthenticationSuccess()
            _state.update {
                it.copy(
                    messageResId = R.string.label_logged_in,
                    biometricsPromptData = null
                )
            }
            delay(600)
            _state.update { it.copy(isAuthenticated = true) }
        }
    }
}