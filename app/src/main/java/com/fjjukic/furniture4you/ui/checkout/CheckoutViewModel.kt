package com.fjjukic.furniture4you.ui.checkout

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MockRepository.getCheckoutViewState())
    val uiState: StateFlow<CheckoutViewState> = _uiState

    fun onDeliveryOptionSelected(optionId: String) {
        _uiState.update {
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

    fun onPaymentInfoChanged(paymentInfo: PaymentInfo) {
        _uiState.update {
            it.copy(
                paymentInfo = paymentInfo
            )
        }
    }

    fun onShippingInfoChanged(shippingInfo: ShippingInfo) {
        _uiState.update {
            it.copy(
                shippingInfo = shippingInfo
            )
        }
    }

}