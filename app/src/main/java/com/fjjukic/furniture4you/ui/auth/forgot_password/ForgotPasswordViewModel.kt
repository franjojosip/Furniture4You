package com.fjjukic.furniture4you.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {
    private val _messageResId = MutableStateFlow<Int?>(null)
    val messageResId = _messageResId.asStateFlow()

    fun resetPassword() {
        _messageResId.value = R.string.label_new_feature
    }
}