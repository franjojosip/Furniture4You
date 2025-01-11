package com.fjjukic.furniture4you

import android.app.Application
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.hilt.android.HiltAndroidApp
import java.security.KeyStore
import javax.crypto.KeyGenerator

@HiltAndroidApp
class FurnitureApplication : Application() {
    val encryptedStorage by lazy {
        createKeyIfNotExist()

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
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(masterKeyUri)
            .build()
            .keysetHandle

        keysetHandle.getPrimitive(Aead::class.java)
    }

    private fun createKeyIfNotExist() {
        val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        if (!keyStore.containsAlias("main_storage_key")) {
            val keyGenSpec = KeyGenParameterSpec.Builder(
                "main_storage_key",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(keyGenSpec)
            keyGenerator.generateKey()
        }
    }
}