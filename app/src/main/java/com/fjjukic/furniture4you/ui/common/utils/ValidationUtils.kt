package com.fjjukic.furniture4you.ui.common.utils

object ValidationUtils {
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        return email.isNotBlank() && email.matches(emailRegex)
    }

    fun isPasswordValid(value: String): Boolean {
        return value.isNotBlank() && value.length > 4 && value.length < 12
    }
}