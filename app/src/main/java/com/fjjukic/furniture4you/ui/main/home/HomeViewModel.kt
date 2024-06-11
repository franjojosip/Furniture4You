package com.fjjukic.furniture4you.ui.main.home

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeState = MutableStateFlow(
        HomeScreenState(MockRepository.getCategories(), MockRepository.getProducts())
    )
    val homeState = _homeState.asStateFlow()

    fun setSelectedCategory(index: Int) {
        _homeState.update { it.copy(selectedCategory = index) }
    }
}