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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.checkout.ShippingAddress
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.payment.DefaultPaymentCheckbox
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ShippingAddressScreenPreview() {
    ShippingAddressScreen(onAddressAddClick = {}, onBackClick = {})
}

@Composable
fun ShippingAddressScreen(
    onBackClick: () -> Unit,
    onAddressAddClick: () -> Unit,
    viewModel: ShippingAddressViewModel = hiltViewModel()
) {

    val shippingAddresses by viewModel.shippingAddresses.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_shipping_address),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(52.dp),
                containerColor = colorResource(id = R.color.color_white),
                contentColor = colorResource(id = R.color.bg_fab_content),
                shape = CircleShape,
                onClick = onAddressAddClick,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.label_add_shipping_address)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
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
                    onShippingInfoChange = viewModel::onEditClick,
                    modifier = Modifier.padding(bottom = 20.dp),
                    hasHeader = false
                )
            }
        }
    }
}