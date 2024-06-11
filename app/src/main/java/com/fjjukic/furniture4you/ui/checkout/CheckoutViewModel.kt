package com.fjjukic.furniture4you.ui.checkout

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MockRepository.getCheckoutViewState())
    val state = _state.asStateFlow()

    fun onDeliveryOptionSelect(optionId: String) {
        _state.update {
            val selectedDelivery =
                it.deliveryOptions.first { deliveryOption -> deliveryOption.id == optionId }
            it.copy(
                selectedDelivery = selectedDelivery,
                priceInfo = PriceInfo(
                    it.priceInfo.orderPrice,
                    selectedDelivery.price
                )
            )
        }
    }

    fun onPaymentInfoChange(paymentInfo: PaymentInfo) {
        _state.update {
            it.copy(
                paymentInfo = paymentInfo
            )
        }
    }

    fun onShippingInfoChange(shippingInfo: ShippingInfo) {
        _state.update {
            it.copy(
                shippingInfo = shippingInfo
            )
        }
    }

}