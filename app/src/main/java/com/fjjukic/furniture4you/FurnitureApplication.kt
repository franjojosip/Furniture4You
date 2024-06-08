package com.fjjukic.furniture4you

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FurnitureApplication : Application() {
    val encryptedStorage by lazy {
        EncryptedSharedPreferences.create(
            "main_storage",
            "main_storage_key",
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    val pinSecuredAead: Aead by lazy {
        AeadConfig.register()

        val keysetName = "pin_secured_keyset"
        val prefFileName = "pin_secured_key_preference"
        val masterKeyUri = "android-keystore://pin_secured_key"

        val keysetHandle: KeysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(
                this,
                keysetName,
                prefFileName
            )
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(masterKeyUri)
            .build()
            .keysetHandle

        keysetHandle.getPrimitive(Aead::class.java)
    }
}