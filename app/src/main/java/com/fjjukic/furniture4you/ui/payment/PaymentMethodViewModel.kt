package com.fjjukic.furniture4you.ui.payment

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.PaymentCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class PaymentMethodScreenState(
    val cards: List<PaymentCard>,
    val mockCard: PaymentCard
)

@HiltViewModel
class PaymentMethodViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        PaymentMethodScreenState(
            cards = MockRepository.getPaymentCards(),
            mockCard = MockRepository.getMockCard()
        )
    )
    val state = _state.asStateFlow()

    fun onCheckboxCheck(id: String) {
        _state.update {
            it.copy(
                cards = it.cards.map { card ->
                    card.copy(
                        isDefault = card.id == id
                    )
                }
            )
        }
    }
}