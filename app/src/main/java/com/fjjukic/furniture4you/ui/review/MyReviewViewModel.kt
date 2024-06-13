package com.fjjukic.furniture4you.ui.review

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MyReviewScreenState(MockRepository.getMyReviews()))
    val state = _state.asStateFlow()
}