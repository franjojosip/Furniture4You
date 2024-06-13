package com.fjjukic.furniture4you.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun getStartDestination(): String {
        val isLogged = mainRepository.isLoggedIn()
        val showPrelogin = mainRepository.getShowPrelogin()
        return when {
            !isLogged && showPrelogin -> Screens.Prelogin.route
            !isLogged -> Graph.AUTH
            else -> Graph.HOME
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.logout()
        }
    }

}