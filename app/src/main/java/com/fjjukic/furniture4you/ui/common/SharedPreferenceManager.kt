package com.fjjukic.furniture4you.ui.common

import android.content.Context
import android.content.SharedPreferences
import com.fjjukic.furniture4you.ui.common.viewmodel.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


object StorageKey {
    const val EMAIL = "email"
    const val PASSWORD = "password"
    const val SHOW_PRELOGIN = "show_prelogin"
    const val USER = "show_prelogin"
}

@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FurniturePrefs", Context.MODE_PRIVATE)

    fun setUser(value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(StorageKey.EMAIL, value)
        editor.apply()
    }

    fun setShowPrelogin(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(StorageKey.SHOW_PRELOGIN, value)
        editor.apply()
    }

    fun getShowPrelogin(): Boolean {
        return sharedPreferences.getBoolean(StorageKey.SHOW_PRELOGIN, true)
    }

    fun savePassword(password: String) {
        sharedPreferences.edit().putString(StorageKey.PASSWORD, password).apply()
    }

    fun saveUser(user: User) {
        val data = gson.toJson(user)
        sharedPreferences.edit().putString(StorageKey.USER, data).apply()
    }

    fun getUser(): User? {
        val data = sharedPreferences.getString(StorageKey.USER, null)
        return gson.fromJson(data, User::class.java)
    }
}