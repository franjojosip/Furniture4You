package com.fjjukic.furniture4you.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.repository.AuthenticationState
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class User(
    val name: String,
    val email: String,
    val salt: String
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _showMessage = MutableStateFlow<Int?>(null)
    val showMessage: StateFlow<Int?> = _showMessage

    private val _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    private val _isAuthenticated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun getStartDestination(): String {
        val isLogged = mainRepository.isLoggedIn()
        val showPrelogin = mainRepository.getShowPrelogin()
        return when {
            !isLogged && showPrelogin -> Screens.Prelogin.route
            !isLogged -> Graph.AUTH
            else -> Graph.HOME
        }
    }

    fun onPreloginShown() {
        mainRepository.onPreloginShown()
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.logout()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (ValidationUtils.isEmailValid(email) && ValidationUtils.isPasswordValid(password)) {
                _showLoading.value = true
                val result = mainRepository.login(email, password)
                _showLoading.value = false
                when (result) {
                    AuthenticationState.AUTHENTICATED -> {
                        _isAuthenticated.value = true
                    }

                    else -> {
                        _showMessage.value = R.string.error_invalid_credentials
                    }
                }
            } else {
                _showMessage.value = R.string.error_invalid_credentials
            }
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isNotBlank()
            && ValidationUtils.isEmailValid(email)
            && ValidationUtils.isPasswordValid(password)
            && password == confirmPassword
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                _showLoading.value = true
                mainRepository.register(name, email, password)
                _isAuthenticated.value = true
                _showLoading.value = false
            }
        } else {
            _showMessage.value = R.string.error_check_fields
        }
    }
}