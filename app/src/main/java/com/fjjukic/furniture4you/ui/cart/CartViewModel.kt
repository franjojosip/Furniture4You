package com.fjjukic.furniture4you.ui.cart

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.CartProduct
import com.fjjukic.furniture4you.ui.common.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        CartScreenState(MockRepository.getCartProducts())
    )
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(price = calculatePrice(it.products))
        }
    }

    fun onPromoCodeEnter(newPromoCode: String) {
        if (_state.value.products.isNotEmpty()
            && newPromoCode.isNotBlank()
            && !_state.value.promoCode.equals(newPromoCode, true)
        ) {
            _state.update {
                it.copy(
                    price = calculatePrice(it.products),
                    promoCode = newPromoCode,
                    discount = Random.nextDouble(0.00, 0.30),
                    message = Message(
                        snackbarResId = R.string.label_promo_code_activated,
                        snackbarActionResId = R.string.btn_dismiss
                    )
                )
            }
        } else if (newPromoCode.isBlank()) {
            _state.update {
                it.copy(
                    price = calculatePrice(it.products),
                    promoCode = "",
                    discount = 0.0,
                    message = Message(
                        snackbarResId = R.string.label_promo_code_removed,
                        snackbarActionResId = R.string.btn_dismiss
                    )
                )
            }
        } else {
            _state.update {
                it.copy(
                    message = Message(
                        snackbarResId = R.string.error_promo_code_already_activated,
                        snackbarActionResId = R.string.btn_dismiss
                    )
                )
            }
        }
    }

    fun onRemoveClick(productId: String) {
        val filteredProducts =
            _state.value.products.filter { it.product.id != productId }

        _state.update {
            it.copy(
                products = filteredProducts,
                price = calculatePrice(filteredProducts)
            )
        }
    }

    fun onIncrementClick(productId: String) {
        val count = _state.value.products.first { it.product.id == productId }.count
        updateProductCount(productId, count + 1)
    }

    fun onDecrementClick(productId: String) {
        val count = _state.value.products.first { it.product.id == productId }.count
        if (count > 1) {
            updateProductCount(productId, count - 1)
        }
    }

    private fun updateProductCount(productId: String, count: Int) {
        _state.update {
            val products = it.products.map { item ->
                if (item.product.id == productId) {
                    item.copy(count = count)
                } else {
                    item
                }
            }
            it.copy(
                products = products,
                price = calculatePrice(products)
            )
        }
    }

    private fun calculatePrice(products: List<CartProduct>): Double {
        return products.sumOf { it.product.price.toDouble() * it.count }
    }

    fun onSnackbarShown() {
        _state.update { it.copy(message = null) }
    }
}