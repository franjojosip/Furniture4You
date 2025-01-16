package com.fjjukic.furniture4you.ui.auth.register

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
class RegisterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RegisterScreenState> =
        MutableStateFlow(
            RegisterScreenState(
                isBiometricsAvailable = mainRepository.isBiometricsAvailable()
            )
        )
    val state = _state.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        shouldRequestBiometrics: Boolean = false
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isBlank()
                || !ValidationUtils.isEmailValid(email)
                || !ValidationUtils.isPasswordValid(password)
            ) {
                if (!password.equals(confirmPassword, false)) {
                    _state.update { it.copy(messageResId = R.string.error_password_not_match) }
                } else {
                    _state.update { it.copy(messageResId = R.string.error_check_fields) }
                }
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            val result = mainRepository.register(name, email, password)
            _state.update { it.copy(isLoading = false) }

            when {
                result == AuthenticationState.AUTHENTICATED && shouldRequestBiometrics -> {
                    _state.update { it.copy(shouldRequestBiometrics = true) }
                }

                result == AuthenticationState.AUTHENTICATED -> {
                    _state.update { it.copy(messageResId = R.string.label_successfully_registered) }
                    _state.update { it.copy(isRegistered = true) }
                }

                else -> {
                    _state.update { it.copy(messageResId = R.string.error_invalid_credentials) }
                }
            }
        }
    }

    fun onBiometricsSuccess() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.onBiometricAuthenticationSuccess()
            _state.update { it.copy(messageResId = R.string.label_successfully_registered) }
            delay(400)
            _state.update { it.copy(isRegistered = true) }
        }
    }
}