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

    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (mainRepository.checkIfAppLockedWithBiometrics()) {
            _state.update { it.copy(showBiometricsPrompt = true) }
            return
        }
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

    fun onBiometricActivationSuccess() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    messageResId = R.string.label_logged_in,
                    showBiometricsPrompt = false
                )
            }
            mainRepository.onBiometricAuthenticationSuccess()
            delay(600)
            _state.update { it.copy(isAuthenticated = true) }
        }
    }

    fun onBiometricActivationFailed() {
        _state.update { it.copy(showBiometricsPrompt = false) }
    }
}