package com.fjjukic.furniture4you.ui.shipping.add


import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.components.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShippingAddressAddViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        ShippingAddressAddScreenState(
            shippingAddresses = MockRepository.getShippingAddresses(),
            countries = MockRepository.getCountries(),
            cities = listOf(),
            newShippingAddress = ShippingInfo()
        )
    )
    val state = _state.asStateFlow()

    fun onCountrySelect(country: MenuItem.Country) {
        if (country.id != _state.value.newShippingAddress.country?.id) {
            val cities = MockRepository.getCities()
            _state.update {
                it.copy(
                    cities = cities.filter { city -> city.countryId == country.id },
                    newShippingAddress = it.newShippingAddress.copy(country = country, city = null)
                )
            }
        }
    }

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

    fun onNameChange(value: String) {
        _state.update {
            it.copy(newShippingAddress = it.newShippingAddress.copy(fullName = value))
        }
    }

    fun onAddressChange(value: String) {
        _state.update {
            it.copy(newShippingAddress = it.newShippingAddress.copy(address = value))
        }
    }

    fun onZipCodeChange(value: String) {
        _state.update {
            it.copy(newShippingAddress = it.newShippingAddress.copy(zipCode = value))
        }
    }

    fun onCitySelect(city: MenuItem.City) {
        _state.update {
            it.copy(newShippingAddress = it.newShippingAddress.copy(city = city))
        }
    }
}