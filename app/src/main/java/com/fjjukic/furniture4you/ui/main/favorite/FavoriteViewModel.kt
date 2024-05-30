package com.fjjukic.furniture4you.ui.main.favorite

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _products = MutableStateFlow(MockRepository.getProducts())
    val products: StateFlow<List<Product>> = _products

    fun onRemoveClick(productId: String) {
        _products.value = _products.value.filter {
            it.id != productId
        }
    }
}