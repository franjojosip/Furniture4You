package com.fjjukic.furniture4you.ui.productdetail

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor() : ViewModel() {
    private val _productState = MutableStateFlow(MockRepository.getProductDetailState())
    val productState: StateFlow<ProductDetailViewState> = _productState

    fun onIncrementClick() {
        _productState.update { it.copy(counter = it.counter + 1) }
    }

    fun onDecrementClick() {
        if (_productState.value.counter > 1) {
            _productState.update { it.copy(counter = it.counter - 1) }
        }
    }
}