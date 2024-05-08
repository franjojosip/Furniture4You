package com.fjjukic.furniture4you.ui.payment

data class PaymentCard(
    val cardNumber: String,
    val cardHolder: String,
    val expDate: String,
    val cvv: String,
    val isDefault: Boolean = false
)
