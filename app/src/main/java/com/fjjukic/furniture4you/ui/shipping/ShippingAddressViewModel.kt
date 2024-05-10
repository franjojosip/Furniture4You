package com.fjjukic.furniture4you.ui.shipping


import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShippingAddressViewModel @Inject constructor() : ViewModel() {

    private val _shippingAddresses = MutableStateFlow(MockRepository.getShippingAddresses())
    val shippingAddresses: StateFlow<List<ShippingInfo>> = _shippingAddresses

    fun onCheckboxCheck(id: String) {
        _shippingAddresses.update {
            it.toMutableList().map { card ->
                card.copy(
                    isDefault = card.id == id
                )
            }
        }
    }

    fun onEditClick(value: ShippingInfo) {
        _shippingAddresses.update { list ->
            list.toMutableList().map { shippingInfo ->
                shippingInfo.copy(
                    fullName = value.fullName,
                    address = value.address
                )
            }
        }
    }
}