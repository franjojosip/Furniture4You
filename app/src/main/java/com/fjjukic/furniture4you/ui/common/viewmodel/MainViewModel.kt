package com.fjjukic.furniture4you.ui.common.viewmodel

import android.util.Base64
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.FurnitureApplication
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.Pbkdf2Factory
import com.fjjukic.furniture4you.ui.common.SharedPreferenceManager
import com.fjjukic.furniture4you.ui.common.StorageKey
import com.fjjukic.furniture4you.ui.common.utils.ValidationUtils
import com.fjjukic.furniture4you.ui.navigation.Graph
import com.fjjukic.furniture4you.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.security.SecureRandom
import javax.inject.Inject


data class User(
    val name: String,
    val email: String,
    val password: String
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefManager: SharedPreferenceManager,
    private val app: FurnitureApplication
) : ViewModel() {

    private val _showMessage = MutableStateFlow<Int?>(null)
    val showMessage: StateFlow<Int?> = _showMessage

    private val fakeAccessToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWNyZXQiOiJXZSdyZSBoaXJpbmcgOykifQ.WZrEWG-l3VsJzJrbnjn2BIYO68gHIGyat6jrw7Iu-Rw"

    private val preferences by lazy {
        app.encryptedStorage
    }

    private val aead by lazy { app.pinSecuredAead }

    fun getStartDestination(): String {
        val isLogged = false//prefManager.getUser().isNotBlank()
        val showPrelogin = prefManager.getShowPrelogin()
        return when {
            !isLogged && showPrelogin -> Screens.Prelogin.route
            !isLogged -> Graph.AUTH
            else -> Graph.HOME
        }
    }

    fun onPreloginShown() {
        prefManager.setShowPrelogin(false)
    }

    fun logout() {
        prefManager.setUser("")
    }

    fun login(email: String, password: String) {
        prefManager.setUser("test123")
        savePassword(password)
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isNotBlank()
            && ValidationUtils.isEmailValid(email)
            && ValidationUtils.isPasswordValid(password)
            && password == confirmPassword
        ) {
            
        } else {
            _showMessage.value = R.string.error_check_fields
        }
    }

    fun savePassword(password: String) {
        val salt = generateSalt()
        val secretKey = Pbkdf2Factory.createKey(password.toCharArray(), salt)

        val encryptedToken = aead.encrypt(
            fakeAccessToken.toByteArray(),
            secretKey.encoded
        )

        val encodedEncryptedToken = Base64.encodeToString(encryptedToken, Base64.DEFAULT)
        val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)

        preferences.edit {
            putString(StorageKey.TOKEN, encodedEncryptedToken)
            putString(StorageKey.SALT, encodedSalt)
            putBoolean(StorageKey.PIN_IS_ENABLED, true)
        }
    }

    fun generateSalt(lengthByte: Int = 32): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(lengthByte)

        random.nextBytes(salt)

        return salt
    }

    enum class AuthenticationState {
        AUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = MutableStateFlow(AuthenticationState.AUTHENTICATED)

    fun authenticate(password: String) {
        authenticationState.value = if (passwordIsValid(password)) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.INVALID_AUTHENTICATION
        }
    }

    private fun passwordIsValid(pin: String): Boolean {
        val salt = Base64.decode(
            preferences.getString(StorageKey.SALT, null), Base64.DEFAULT
        )
        val secretKey = Pbkdf2Factory.createKey(pin.toCharArray(), salt)

        val token = try {
            val encryptedToken = Base64.decode(
                preferences.getString(StorageKey.TOKEN, null), Base64.DEFAULT
            )

            aead.decrypt(encryptedToken, secretKey.encoded)
        } catch (e: GeneralSecurityException) {
            null
        }

        return token?.isNotEmpty() ?: false
    }
}