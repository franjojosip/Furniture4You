package com.fjjukic.furniture4you.ui.payment

import androidx.annotation.DrawableRes
import java.util.UUID

data class PaymentCard(
    val id: String = UUID.randomUUID().toString(),
    val cardNumber: String,
    val cardHolder: String,
    val expDate: String,
    val cvv: String,
    @DrawableRes
    val vendorLogoResId: Int,
    val isDefault: Boolean = false
)
