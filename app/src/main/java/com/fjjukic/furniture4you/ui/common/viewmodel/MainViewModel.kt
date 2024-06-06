package com.fjjukic.furniture4you.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.SharedPreferenceManager
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


data class User(
    val email: String,
    val password: String
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefManager: SharedPreferenceManager
) : ViewModel() {

    fun getStartDestination(): String {
        val isLogged = false//prefManager.getUser().isNotBlank()
        val showPrelogin = prefManager.getShowPrelogin()
        return when {
            !isLogged && showPrelogin -> Screens.Prelogin.route
            !isLogged -> Graph.AUTH
            else -> Graph.HOME
        }
    }

    fun onPreloginShown() {
        prefManager.setShowPrelogin(false)
    }

    fun logout() {
        prefManager.setUser("")
    }

    fun login(email: String, password: String) {
        prefManager.setUser("test123")

        prefManager.saveUser(User(email, password))
        prefManager.savePassword(password)
    }


    enum class AuthenticationState {
        AUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = MutableStateFlow(AuthenticationState.AUTHENTICATED)

    fun authenticate(password: String) {
        authenticationState.value = if (passwordIsValid(password)) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.INVALID_AUTHENTICATION
        }
    }

    private fun passwordIsValid(password: String): Boolean {
        return prefManager.getUser()?.password == password
    }
}