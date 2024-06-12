package com.fjjukic.furniture4you.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    fun isBiometricsAvailable(): Boolean {
        return mainRepository.isBiometricsAvailable()
    }

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        useBiometrics: Boolean?
    ) {
        if (name.isBlank()
            || !ValidationUtils.isEmailValid(email)
            || !ValidationUtils.isPasswordValid(password)
            || !password.equals(confirmPassword, false)
        ) {
            _state.update { it.copy(messageResId = R.string.error_check_fields) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            val result = mainRepository.register(name, email, password)
            _state.update { it.copy(isLoading = false) }

            if (result == AuthenticationState.AUTHENTICATED) {

                if (useBiometrics == true) {
                    _state.update { it.copy(shouldRequestBiometrics = true) }
                } else {
                    _state.update { it.copy(messageResId = R.string.label_successfully_registered) }
                    _state.update { it.copy(isRegistered = true) }
                }
            } else {
                _state.update { it.copy(messageResId = R.string.error_invalid_credentials) }
            }
        }
    }
}