package com.fjjukic.furniture4you.ui.checkout

import java.util.UUID

data class CheckoutViewState(
    val selectedDelivery: DeliveryOption,
    val deliveryOptions: List<DeliveryOption>,
    val priceInfo: PriceInfo,
    val shippingInfo: ShippingInfo,
    val paymentInfo: PaymentInfo
)

data class PriceInfo(
    val orderPrice: Double,
    val deliveryPrice: Double,
)

data class DeliveryOption(
    val id: String = UUID.randomUUID().toString(),
    val iconResId: Int,
    val description: String,
    val price: Double
)

data class ShippingInfo(
    val fullName: String,
    val address: String
)


data class PaymentInfo(
    val cardHolder: String,
    val cardNumber: String,
    val cvv: String,
    val expDate: String,
    val maxCardNum: Int = 16
)
