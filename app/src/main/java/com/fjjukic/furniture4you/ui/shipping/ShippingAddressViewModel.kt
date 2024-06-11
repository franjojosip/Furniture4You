package com.fjjukic.furniture4you.ui.shipping


import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShippingAddressViewModel @Inject constructor() : ViewModel() {

    private val _state =
        MutableStateFlow(ShippingAddressScreenState(shippingAddresses = MockRepository.getShippingAddresses()))
    val state = _state.asStateFlow()

    fun onCheckboxCheck(id: String) {
        _state.update {
            it.copy(
                shippingAddresses = it.shippingAddresses.map { card ->
                    card.copy(isDefault = card.id == id)
                }
            )
        }
    }

    fun onEditClick(value: ShippingInfo) {
        _state.update {
            it.copy(
                shippingAddresses = it.shippingAddresses.map { shippingInfo ->
                    if (value.id == shippingInfo.id) {
                        shippingInfo.copy(
                            fullName = value.fullName,
                            address = value.address
                        )
                    } else shippingInfo
                }
            )
        }
    }
}