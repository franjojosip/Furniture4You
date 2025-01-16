package com.fjjukic.furniture4you.ui.common.crypto

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.KeyStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPrefsManager @Inject constructor(private val context: Context) {

    companion object {
        private const val TAG = "EncryptedPrefsManager"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val ENCRYPTED_FILE_NAME = "encrypted_main_storage"
    }

    private val masterKeyAlias by lazy {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    private val encryptedPreferences: SharedPreferences by lazy {
        build()
    }

    private fun build(): SharedPreferences {
        return try {
            createSharedPreferences()
        } catch (e: Exception) {
            Log.e(TAG, "Error occurred while create shared preference")
            deleteSharedPreferences()
            deleteMasterKey()
            createSharedPreferences()
        }
    }

    private fun createSharedPreferences() = EncryptedSharedPreferences.create(
        ENCRYPTED_FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun clearSharedPreference() {
        context.getSharedPreferences(ENCRYPTED_FILE_NAME, Context.MODE_PRIVATE).edit().clear()
            .apply()
    }

    private fun deleteSharedPreferences() {
        try {
            clearSharedPreference()
        } catch (e: Exception) {
            Log.e(TAG, "Error delete preferences")
        }
    }

    private fun deleteMasterKey() {
        try {
            val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
            keyStore.load(null)
            keyStore.deleteEntry(masterKeyAlias)
        } catch (e: Exception) {
            Log.e(TAG, "Error delete MasterKey")
        }
    }

    fun putString(key: String, value: String) {
        encryptedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return encryptedPreferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        encryptedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return encryptedPreferences.getBoolean(key, defaultValue)
    }

    fun clear() {
        encryptedPreferences.edit().clear().apply()
    }
}