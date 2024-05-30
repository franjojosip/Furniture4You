package com.fjjukic.furniture4you.ui.shipping


import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.components.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShippingAddressViewModel @Inject constructor() : ViewModel() {

    private val _shippingAddresses = MutableStateFlow(MockRepository.getShippingAddresses())
    val shippingAddresses: StateFlow<List<ShippingInfo>> = _shippingAddresses

    private val _countries = MutableStateFlow(MockRepository.getCountries())
    val countries: StateFlow<List<MenuItem.Country>> = _countries

    private val _cities = MutableStateFlow<List<MenuItem.City>>(listOf())
    val cities: StateFlow<List<MenuItem.City>> = _cities


    private val _newShippingAddress = MutableStateFlow(ShippingInfo())
    val newShippingAddress: StateFlow<ShippingInfo> = _newShippingAddress

    fun onCountrySelect(country: MenuItem.Country) {
        if (country.id != _newShippingAddress.value.country?.id) {
            val cities = MockRepository.getCities()
            _cities.value = cities.filter { it.countryId == country.id }
            _newShippingAddress.update {
                it.copy(country = country, city = null)
            }
        }
    }

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
                if (value.id == shippingInfo.id) {
                    shippingInfo.copy(
                        fullName = value.fullName,
                        address = value.address
                    )
                } else shippingInfo
            }
        }
    }

    fun onNameChange(value: String) {
        _newShippingAddress.update {
            it.copy(fullName = value)
        }
    }

    fun onAddressChange(value: String) {
        _newShippingAddress.update {
            it.copy(address = value)
        }
    }

    fun onZipCodeChange(value: String) {
        _newShippingAddress.update {
            it.copy(zipCode = value)
        }
    }

    fun onCitySelect(city: MenuItem.City) {
        _newShippingAddress.update {
            it.copy(city = city)
        }
    }
}