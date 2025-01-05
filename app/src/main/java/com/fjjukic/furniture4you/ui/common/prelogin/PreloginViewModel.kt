package com.fjjukic.furniture4you.ui.common.prelogin

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PreloginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _preloginShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val preloginShown = _preloginShown.asStateFlow()

    fun onPreloginShown() {
        mainRepository.onPreloginShown()
        _preloginShown.value = true
    }
}