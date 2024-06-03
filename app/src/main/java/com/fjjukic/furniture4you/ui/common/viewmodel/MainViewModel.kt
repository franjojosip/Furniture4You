package com.fjjukic.furniture4you.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val prefManager: SharedPreferenceManager) :
    ViewModel() {
}