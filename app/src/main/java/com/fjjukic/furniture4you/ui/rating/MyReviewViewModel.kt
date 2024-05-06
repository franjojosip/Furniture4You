package com.fjjukic.furniture4you.ui.rating

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MockRepository.getMyReviews())
    val uiState: StateFlow<List<MyReviewModel>> = _uiState
}