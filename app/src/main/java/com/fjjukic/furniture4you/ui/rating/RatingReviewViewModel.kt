package com.fjjukic.furniture4you.ui.rating

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RatingReviewViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        RatingReviewViewState(
            MockRepository.getProductDetail(),
            MockRepository.getReviews()
        )
    )
    val state: StateFlow<RatingReviewViewState> = _state
}