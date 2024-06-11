package com.fjjukic.furniture4you.ui.productdetail

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        ProductDetailViewState(MockRepository.getProductDetail())
    )
    val state = _state.asStateFlow()

    fun onIncrementClick() {
        _state.update { it.copy(counter = it.counter + 1) }
    }

    fun onDecrementClick() {
        if (_state.value.counter > 1) {
            _state.update { it.copy(counter = it.counter - 1) }
        }
    }
}