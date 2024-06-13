package com.fjjukic.furniture4you.ui.main.favorite

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(FavoriteScreenState(MockRepository.getProducts()))
    val state = _state.asStateFlow()

    fun onRemoveClick(productId: String) {
        _state.update {
            it.copy(products = it.products.filter { product -> product.id != productId })
        }
    }
}