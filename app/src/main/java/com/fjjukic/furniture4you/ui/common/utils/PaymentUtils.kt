package com.fjjukic.furniture4you.ui.common.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale

object PaymentUtils {
    fun formatPrice(value: Double): String {
        return String.format(Locale.getDefault(), "%.2f", value)
    }

    fun maskCardNumber(cardNumber: String): String {
        return cardNumber.chunked(4).joinToString(separator = " ")
            .replace("\\d(?=.*\\d{4})".toRegex(), "*")
    }

    fun isValidCardInformation(
        cardHolder: String,
        cardNumber: String,
        cvv: String,
        expDate: String
    ): Boolean {
        return cardHolder.isNotEmpty() && isValidCardNumber(cardNumber)
                && isValidCVV(cvv) && isValidExpDate(expDate)
    }

    fun isValidCVV(cvv: String): Boolean {
        return cvv.length == 3
    }

    fun isValidExpDate(date: String): Boolean {
        return date.length == 4 && "^(0[1-9]|1[0-2])([2-9][0-9])$".toRegex().matches(date)
    }

    fun isValidCardNumber(cardNumber: String): Boolean {
        // Remove any whitespace from the card number
        val trimmedCardNumber = cardNumber.replace("\\s".toRegex(), "")

        // Check if the card number has exactly 16 digits
        if (trimmedCardNumber.length != 16) {
            return false
        }

        // Perform Luhn algorithm checksum validation
        var checksum = 0
        for (i in trimmedCardNumber.indices) {
            val digit = trimmedCardNumber[i] - '0'
            if (i % 2 == 0) {
                var doubledDigit = digit * 2
                if (doubledDigit > 9) {
                    doubledDigit -= 9
                }
                checksum += doubledDigit
            } else {
                checksum += digit
            }
        }

        return checksum % 10 == 0
    }
}

class CardNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text.text.chunked(4).joinToString(separator = " ")

        val creditCardOffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in (0..4) -> offset
                    in (5..8) -> offset + 1
                    in (9..12) -> offset + 2
                    in (13..16) -> offset + 3
                    else -> 19
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in (0..4) -> offset
                    in (5..9) -> offset - 1
                    in (10..14) -> offset - 2
                    in (15..19) -> offset - 3
                    else -> 16
                }
            }
        }
        return TransformedText(AnnotatedString(transformedText), creditCardOffsetMapping)
    }
}

class ExpDateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text.text.chunked(2).joinToString(separator = "/")

        val expDateOffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in (0..2) -> offset
                    in (3..4) -> offset + 1
                    else -> 5
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in (0..3) -> offset
                    in (4..6) -> offset - 1
                    else -> 5
                }
            }
        }
        return TransformedText(AnnotatedString(transformedText), expDateOffsetMapping)
    }
}