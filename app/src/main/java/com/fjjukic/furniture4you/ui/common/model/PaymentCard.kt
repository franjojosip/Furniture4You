package com.fjjukic.furniture4you.ui.common.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
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
