package com.fjjukic.furniture4you.ui.payment

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor() : ViewModel() {

    private val _cards = MutableStateFlow(MockRepository.getPaymentCards())
    val cards: StateFlow<List<PaymentCard>> = _cards

    private val _mockCard = MutableStateFlow(MockRepository.getMockCard())
    val mockCard: StateFlow<PaymentCard> = _mockCard

    fun onCheckboxCheck(id: String) {
        _cards.update {
            it.toMutableList().map { card ->
                card.copy(
                    isDefault = card.id == id
                )
            }
        }
    }
}