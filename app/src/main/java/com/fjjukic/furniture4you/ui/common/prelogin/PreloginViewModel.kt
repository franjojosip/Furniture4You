package com.fjjukic.furniture4you.ui.common.prelogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreloginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _preloginShown: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val preloginShown = _preloginShown.asStateFlow()

    fun onPreloginShown() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.onPreloginShown()
            _preloginShown.value = true
        }
    }
}