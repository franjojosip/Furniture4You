package com.fjjukic.furniture4you.ui.shipping.add

import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.components.MenuItem

data class ShippingAddressAddScreenState(
    val shippingAddresses: List<ShippingInfo>,
    val countries: List<MenuItem.Country>,
    val cities: List<MenuItem.City>,
    val newShippingAddress: ShippingInfo
)