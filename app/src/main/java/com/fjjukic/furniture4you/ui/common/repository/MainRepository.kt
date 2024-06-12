package com.fjjukic.furniture4you.ui.common.repository

import android.content.Context
import android.util.Base64
import androidx.biometric.BiometricManager
import androidx.core.content.edit
import com.fjjukic.furniture4you.FurnitureApplication
import com.fjjukic.furniture4you.ui.common.biometrics.BiometricsHelper
import com.fjjukic.furniture4you.ui.common.utils.Pbkdf2Factory
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.common.viewmodel.User
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.security.GeneralSecurityException
import java.security.SecureRandom
import javax.inject.Inject


interface MainRepository {
    fun getShowPrelogin(): Boolean
    fun onPreloginShown()
    fun isLoggedIn(): Boolean

    suspend fun login(email: String, password: String): AuthenticationState
    suspend fun register(name: String, email: String, password: String): AuthenticationState
    suspend fun logout()

    fun isBiometricsAvailable(): Boolean
    fun checkBiometricsAvailable(): BiometricsAvailability
    fun setupLockWithBiometrics(isLocked: Boolean)
    fun checkIfAppLockedWithBiometrics(): Boolean

    fun onBiometricAuthenticationSuccess()
}

enum class AuthenticationState {
    AUTHENTICATED, INVALID_AUTHENTICATION
}

class MainRepositoryImpl @Inject constructor(
    private val app: Context
) : MainRepository {

    companion object {
        private const val MOCK_LOGIN_EMAIL = "test@mail.com"
        private const val MOCK_PASSWORD = "test123"
        private const val MOCK_DELAY = 1000L
    }

    private val fakeAccessToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWNyZXQiOiJXZSdyZSBoaXJpbmcgOykifQ.WZrEWG-l3VsJzJrbnjn2BIYO68gHIGyat6jrw7Iu-Rw"

    private val securedPreferences by lazy {
        (app as FurnitureApplication).encryptedStorage
    }

    private val aead by lazy { (app as FurnitureApplication).pinSecuredAead }
    private val gson by lazy { Gson() }


    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthenticationState {
        delay(MOCK_DELAY)
        // This is for encryption show case
        // Usually we authenticate with email/password on server and then server will return an access token
        // Access token can be used to extend login types like PIN code, biometrics, etc.
        saveUser(name, email, password)
        return AuthenticationState.AUTHENTICATED
    }

    override fun isLoggedIn(): Boolean {
        return securedPreferences.getBoolean(StorageKey.IS_LOGGED_IN, false)
    }

    private fun saveUser(name: String, email: String, password: String) {
        val salt = generateSalt()
        val secretKey = Pbkdf2Factory.createKey(password.toCharArray(), salt)

        val encryptedToken = aead.encrypt(
            fakeAccessToken.toByteArray(),
            secretKey.encoded
        )

        val encodedEncryptedToken = Base64.encodeToString(encryptedToken, Base64.DEFAULT)
        val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)

        val user = gson.toJson(User(name, email, encodedSalt))

        securedPreferences.edit {
            putString(StorageKey.USER, user)
            putString(StorageKey.TOKEN, encodedEncryptedToken)
            putBoolean(StorageKey.PIN_IS_ENABLED, true)
        }
    }

    private fun generateSalt(lengthByte: Int = 32): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(lengthByte)

        random.nextBytes(salt)

        return salt
    }

    override suspend fun login(email: String, password: String): AuthenticationState {
        delay(MOCK_DELAY)
        return when {
            email == MOCK_LOGIN_EMAIL && password == MOCK_PASSWORD -> {
                securedPreferences.edit {
                    putBoolean(StorageKey.IS_LOGGED_IN, true)
                }
                AuthenticationState.AUTHENTICATED
            }

            !ValidationUtils.isEmailValid(email) || !ValidationUtils.isPasswordValid(password) -> AuthenticationState.INVALID_AUTHENTICATION
            else -> checkAuthData(email, password)
        }
    }

    private fun checkAuthData(email: String, password: String): AuthenticationState {
        val userJson: String = securedPreferences.getString(StorageKey.USER, null)
            ?: return AuthenticationState.INVALID_AUTHENTICATION

        val user: User = gson.fromJson(userJson, User::class.java)

        if (!user.email.equals(email, true)) return AuthenticationState.INVALID_AUTHENTICATION

        val salt = Base64.decode(user.salt, Base64.DEFAULT)
        val secretKey = Pbkdf2Factory.createKey(password.toCharArray(), salt)

        val token = try {
            val encryptedToken =
                Base64.decode(securedPreferences.getString(StorageKey.TOKEN, null), Base64.DEFAULT)

            aead.decrypt(encryptedToken, secretKey.encoded)
        } catch (e: GeneralSecurityException) {
            null
        }

        return if (token != null) {
            AuthenticationState.AUTHENTICATED
        } else AuthenticationState.INVALID_AUTHENTICATION
    }

    override fun getShowPrelogin(): Boolean {
        return securedPreferences.getBoolean(StorageKey.SHOW_PRELOGIN, true)
    }

    override fun onPreloginShown() {
        securedPreferences.edit {
            putBoolean(StorageKey.SHOW_PRELOGIN, false)
        }
    }

    override suspend fun logout() {
        delay(MOCK_DELAY)
        securedPreferences.edit {
            putBoolean(StorageKey.IS_LOGGED_IN, false)
        }
    }

    override fun setupLockWithBiometrics(isLocked: Boolean) {
        securedPreferences.edit().putBoolean(StorageKey.BIOMETRICS_FLAG, isLocked).apply()
    }

    override fun checkIfAppLockedWithBiometrics(): Boolean {
        return securedPreferences.getBoolean(StorageKey.BIOMETRICS_FLAG, false)
    }

    override fun isBiometricsAvailable(): Boolean {
        return checkBiometricsAvailable() == BiometricsAvailability.Available
    }

    override fun checkBiometricsAvailable(): BiometricsAvailability {
        return when (BiometricsHelper.checkIfBiometricsAvailable(app)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                BiometricsAvailability.Available
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                BiometricsAvailability.NotEnabled
            }
            // All cases where not available
            else -> {
                BiometricsAvailability.NotAvailable
            }
        }
    }

    override fun onBiometricAuthenticationSuccess() {
        securedPreferences.edit {
            putBoolean(StorageKey.IS_LOGGED_IN, true)
        }
    }
}

sealed class BiometricsAvailability {
    object Checking : BiometricsAvailability()
    object Available : BiometricsAvailability()
    object NotAvailable : BiometricsAvailability()
    object NotEnabled : BiometricsAvailability()
}