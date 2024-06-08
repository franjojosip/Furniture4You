package com.fjjukic.furniture4you.ui.common

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.fjjukic.furniture4you.ui.common.viewmodel.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject
import javax.inject.Singleton


object StorageKey {
    const val EMAIL = "email"
    const val PASSWORD = "password"
    const val TOKEN = "token"
    const val SALT = "salt"
    const val PIN_IS_ENABLED = "pin_is_enabled"
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

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(StorageKey.TOKEN, token).apply()
    }

    fun savePassword(password: String) {
        sharedPreferences.edit().putString(StorageKey.PASSWORD, password).apply()
    }

    fun saveSalt(salt: String) {
        sharedPreferences.edit().putString(StorageKey.SALT, salt).apply()
    }

    fun savePinEnabled(isPinEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(StorageKey.PIN_IS_ENABLED, isPinEnabled).apply()
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

object Pbkdf2Factory {
    private const val DEFAULT_ITERATIONS = 10000
    private const val DEFAULT_KEY_LENGTH = 256

    private val secretKeyFactory by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
        } else {
            SecretKeyFactory.getInstance("PBKDF2withHmacSHA256")
        }
    }

    fun createKey(
        passphraseOrPin: CharArray,
        salt: ByteArray,
        iterations: Int = DEFAULT_ITERATIONS,
        outputKeyLength: Int = DEFAULT_KEY_LENGTH
    ): SecretKey {
        val keySpec = PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength)
        return secretKeyFactory.generateSecret(keySpec)
    }
}