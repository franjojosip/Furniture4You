package com.fjjukic.furniture4you.ui.common.utils

import android.util.Patterns

object ValidationUtils {
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(value: String): Boolean {
        return value.isNotBlank() && value.length > 5 && value.length < 12
    }
}