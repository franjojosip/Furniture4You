package com.fjjukic.furniture4you.ui.cart

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.CartPrice
import com.fjjukic.furniture4you.ui.common.model.CartProduct
import com.fjjukic.furniture4you.ui.common.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import ht.ferit.fjjukic.foodlovers.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _products = MutableStateFlow(MockRepository.getCartProducts())
    val products: StateFlow<List<CartProduct>> = _products

    private val _price = MutableStateFlow(CartPrice())
    val price: StateFlow<CartPrice> = _price

    private val _showMessage = MutableStateFlow<Message?>(null)
    val showMessage: StateFlow<Message?> = _showMessage

    private var discount: Double = 0.0
    private var promoCode = ""

    init {
        updatePrice()
    }

    fun onPromoCodeEnter(newPromoCode: String) {
        val isNewPromoCode = promoCode != newPromoCode
        if (isNewPromoCode) {
            promoCode = newPromoCode
            discount = Random.nextDouble(0.00, 0.30)
            updatePrice()
        }
        _showMessage.value = Message(
            snackbarResId = if (isNewPromoCode) R.string.label_promo_code_activated else R.string.error_promo_code_already_activated,
            snackbarActionResId = R.string.btn_dismiss
        )
    }

    fun onRemoveClick(productId: String) {
        _products.value = _products.value.filter {
            it.product.id != productId
        }
        updatePrice()
    }

    fun onIncrementClick(productId: String) {
        val count = _products.value.first { it.product.id == productId }.count
        updateProductCount(productId, count + 1)
    }

    fun onDecrementClick(productId: String) {
        val count = _products.value.first { it.product.id == productId }.count
        if (count > 1) {
            updateProductCount(productId, count - 1)
        }
    }

    private fun updateProductCount(productId: String, count: Int) {
        _products.value = _products.value.map {
            if (it.product.id == productId) {
                it.copy(count = count)
            } else {
                it
            }
        }
        updatePrice()
    }

    private fun updatePrice() {
        val fullPrice = _products.value.sumOf { it.product.price.toDouble() * it.count }
        _price.update { it.copy(price = fullPrice, discount = discount) }
    }

    fun onSnackbarShown() {
        _showMessage.value = null
    }
}