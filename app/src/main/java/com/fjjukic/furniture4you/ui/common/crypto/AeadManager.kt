package com.fjjukic.furniture4you.ui.common.crypto

import android.content.Context
import android.util.Log
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AeadManager @Inject constructor(@ApplicationContext private val context: Context) {

    // Declare pinSecuredAead as a regular property
    private lateinit var pinSecuredAead: Aead

    init {
        // Initialize pinSecuredAead in the init block
        initializePinSecuredAead()
    }

    private fun initializePinSecuredAead() {
        // Register AEAD configuration
        AeadConfig.register()

        // Define keyset parameters
        val keysetName = "pin_secured_keyset"
        val prefFileName = "pin_secured_key_preference"
        val masterKeyUri = "android-keystore://pin_secured_key"

        try {
            // Create or retrieve the keyset
            val keysetHandle = AndroidKeysetManager.Builder()
                .withSharedPref(context, keysetName, prefFileName)
                .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
                .withMasterKeyUri(masterKeyUri)
                .build()
                .keysetHandle

            // Initialize AEAD primitive
            pinSecuredAead = keysetHandle.getPrimitive(Aead::class.java)
        } catch (e: InvalidKeyException) {
            // Handle key loading errors (key is missing or corrupted)
            Log.e("AeadManager", "Keystore cannot load the key, regenerating key", e)
            // Clear corrupted preferences and reinitialize keyset
            context.deleteSharedPreferences(prefFileName)  // Clear preferences
            deleteMasterKey()  // Delete corrupted master key
            regenerateKeyset(keysetName, prefFileName, masterKeyUri)  // Regenerate keyset
            initializePinSecuredAead()  // Re-initialize AEAD
        } catch (e: Exception) {
            Log.e("AeadManager", "Unexpected error while initializing AEAD", e)
            throw IllegalStateException("Failed to initialize AEAD", e)
        }
    }

    // Function to regenerate the keyset if the key was not found
    private fun regenerateKeyset(keysetName: String, prefFileName: String, masterKeyUri: String) {
        try {
            AndroidKeysetManager.Builder()
                .withSharedPref(context, keysetName, prefFileName)
                .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
                .withMasterKeyUri(masterKeyUri)
                .build()  // This will regenerate the keyset
        } catch (e: Exception) {
            Log.e("AeadManager", "Error regenerating keyset", e)
            throw IllegalStateException("Failed to regenerate keyset", e)
        }
    }

    // Function to delete the master key from the Android Keystore
    private fun deleteMasterKey() {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyStore.deleteEntry("pin_secured_key")  // Deleting the master key
        } catch (e: KeyStoreException) {
            Log.e("AeadManager", "Error deleting master key", e)
        }
    }

    // Encryption and decryption methods
    fun encrypt(data: ByteArray, associatedData: ByteArray? = null): ByteArray {
        return pinSecuredAead.encrypt(data, associatedData)
    }

    fun decrypt(encryptedData: ByteArray, associatedData: ByteArray? = null): ByteArray {
        return pinSecuredAead.decrypt(encryptedData, associatedData)
    }
}