package com.fjjukic.furniture4you.ui.auth.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val messageResId: Int? = null
)