package com.fjjukic.furniture4you.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.SharedPreferenceManager
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefManager: SharedPreferenceManager
) : ViewModel() {

    fun getStartDestination(): String {
        val isLogged = prefManager.getUser().isNotBlank()
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

    fun login() {
        prefManager.setUser("test123")
    }

    fun logout() {
        prefManager.setUser("")
    }
}