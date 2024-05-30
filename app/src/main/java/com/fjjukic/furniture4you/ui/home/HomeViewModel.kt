package com.fjjukic.furniture4you.ui.home

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeState = MutableStateFlow(MockRepository.getHomeState())
    val homeState: StateFlow<HomeViewState> = _homeState

    private val _selectedCategory = MutableStateFlow(0)
    val selectedCategory: StateFlow<Int> = _selectedCategory

    fun setSelectedCategory(index: Int) {
        _selectedCategory.update { index }
    }
}