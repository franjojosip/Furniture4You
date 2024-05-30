package com.fjjukic.furniture4you.ui.shipping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.common.fields.OutlinedInputField
import com.fjjukic.furniture4you.ui.components.MenuItem
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.theme.FieldTextColor
import com.fjjukic.furniture4you.ui.theme.GelatioTypography
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R


@Preview
@Composable
fun AddShippingAddressScreenPreview() {
    AddShippingAddressScreen(onBackClick = {})
}

@Composable
fun AddShippingAddressScreen(
    onBackClick: () -> Unit,
    viewModel: ShippingAddressViewModel = hiltViewModel()
) {

    val countries by viewModel.countries.collectAsStateWithLifecycle()
    val availableCities by viewModel.cities.collectAsStateWithLifecycle()

    val newAddress by viewModel.newShippingAddress.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_add_shipping_address),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClick,
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    onClick = {
                        // do nothing
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_save_address).uppercase(),
                        style = GelatioTypography.bodyMedium,
                        color = colorResource(id = R.color.color_white)
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 24.dp),
                value = newAddress.fullName,
                label = stringResource(R.string.field_name),
                placeholder = stringResource(R.string.placeholder_name),
                onValueChange = viewModel::onNameChange,
                isFieldValid = {
                    it.isNotBlank()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                value = newAddress.address,
                label = stringResource(R.string.field_shipping_address),
                placeholder = stringResource(R.string.placeholder_address),
                onValueChange = viewModel::onAddressChange,
                isFieldValid = {
                    it.isNotEmpty()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )

            OutlinedInputField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                value = newAddress.zipCode,
                label = stringResource(R.string.field_shipping_zipcode),
                placeholder = stringResource(R.string.placeholder_zipcode),
                onValueChange = viewModel::onZipCodeChange,
                isFieldValid = {
                    Regex("\\d{5}").matches(it)
                },
                errorMessage = stringResource(R.string.error_invalid_zipcode),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )

            DynamicSelectTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                selectedValue = newAddress.country?.name ?: "",
                label = stringResource(R.string.field_country),
                placeholder = stringResource(R.string.placeholder_country),
                onItemSelect = {
                    viewModel.onCountrySelect(it as MenuItem.Country)
                },
                options = countries
            )

            DynamicSelectTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                selectedValue = newAddress.city?.name ?: "",
                label = stringResource(R.string.field_city),
                placeholder = stringResource(R.string.placeholder_city),
                onItemSelect = {
                    viewModel.onCitySelect(it as MenuItem.City)
                },
                options = availableCities
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<MenuItem>,
    label: String,
    placeholder: String,
    onItemSelect: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = FieldTextColor,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedValue,
                onValueChange = {},
                placeholder = {
                    Text(placeholder, color = colorResource(id = R.color.color_placeholder))
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colorResource(id = R.color.color_white)),
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.color_white)),
                        text = { Text(text = item.name) },
                        onClick = {
                            expanded = false
                            onItemSelect(item)
                        }
                    )
                }
            }
        }
    }
}