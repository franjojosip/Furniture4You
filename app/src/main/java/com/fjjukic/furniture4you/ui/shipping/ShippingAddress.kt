package com.fjjukic.furniture4you.ui.shipping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.cart.Header
import com.fjjukic.furniture4you.ui.checkout.ShippingAddress
import com.fjjukic.furniture4you.ui.payment.DefaultPaymentCheckbox
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ShippingAddressSettingPreview() {
    ShippingAddressSetting(ShippingAddressViewModel(), {}, {})
}

@Composable
fun ShippingAddressSetting(
    viewModel: ShippingAddressViewModel,
    onBackClick: () -> Unit,
    onCardAddClick: () -> Unit
) {

    val shippingAddresses by viewModel.shippingAddresses.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(
                title = stringResource(id = R.string.nav_shipping_address),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(52.dp),
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.color_fab_add_content),
                shape = CircleShape,
                onClick = onCardAddClick,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 20.dp)
        ) {
            items(shippingAddresses) { address ->
                DefaultPaymentCheckbox(
                    { isChecked ->
                        if (!address.isDefault && isChecked) {
                            viewModel.onCheckboxCheck(address.id)
                        }
                    },
                    modifier = Modifier.padding(bottom = 6.dp),
                    isDefaultChecked = address.isDefault
                )
                ShippingAddress(
                    shippingInfo = address,
                    onEditClick = viewModel::onEditClick,
                    modifier = Modifier.padding(bottom = 20.dp),
                    hasHeader = false
                )
            }
        }
    }
}