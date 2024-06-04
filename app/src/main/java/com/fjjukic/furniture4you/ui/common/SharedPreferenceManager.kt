package com.fjjukic.furniture4you.ui.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val USER = "USER"
        const val SHOW_PRELOGIN = "SHOW_PRELOGIN"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FurniturePrefs", Context.MODE_PRIVATE)

    fun setUser(value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER, value)
        editor.apply()
    }

    fun getUser(): String {
        return sharedPreferences.getString(USER, "") ?: ""
    }

    fun setShowPrelogin(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(SHOW_PRELOGIN, value)
        editor.apply()
    }

    fun getShowPrelogin(): Boolean {
        return sharedPreferences.getBoolean(SHOW_PRELOGIN, true)
    }
}