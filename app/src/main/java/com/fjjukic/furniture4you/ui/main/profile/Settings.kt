package com.fjjukic.furniture4you.ui.main.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjjukic.furniture4you.ui.checkout.CheckoutItemHeader
import com.fjjukic.furniture4you.ui.common.Toolbar
import com.fjjukic.furniture4you.ui.main.profile.dialog.PasswordChangeDialog
import com.fjjukic.furniture4you.ui.main.profile.dialog.PersonalInformationChangeDialog
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun SettingsPreview() {
    Settings(SettingsViewModel()) {}
}

@Composable
fun Settings(viewModel: SettingsViewModel, onBackClicked: () -> Unit) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var openPersonalInformationDialog by remember { mutableStateOf(false) }
    var passwordDialog by remember { mutableStateOf(false) }

    if (openPersonalInformationDialog) {
        PersonalInformationChangeDialog(
            uiState.personalInformation,
            onContinueClicked = {
                openPersonalInformationDialog = false
                viewModel.onEditClick(it)
            },
            onDismissClicked = {
                openPersonalInformationDialog = false
            }
        )
    }

    if (passwordDialog) {
        PasswordChangeDialog(
            uiState.password,
            onContinueClicked = {
                passwordDialog = false
                viewModel.onPasswordChange(it)
            },
            onDismissClicked = {
                passwordDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_settings),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = onBackClicked,
                onEndActionClick = {},
                modifier = Modifier.background(Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            CheckoutItemHeader(
                label = stringResource(R.string.personal_information),
                onEditClick = {
                    openPersonalInformationDialog = true
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
            PersonalInformationCard(
                stringResource(id = R.string.label_name),
                uiState.personalInformation.name
            )
            PersonalInformationCard(
                stringResource(id = R.string.label_email),
                uiState.personalInformation.email
            )

            CheckoutItemHeader(
                label = stringResource(R.string.label_password),
                onEditClick = {
                    passwordDialog = true
                },
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 20.dp)
            )
            PersonalInformationCard(
                stringResource(id = R.string.label_password),
                uiState.password.replace(Regex("\\S"), "*")
            )

            CheckoutItemHeader(
                label = stringResource(R.string.label_notification),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 20.dp)
            )

            SwitchField(
                stringResource(id = R.string.label_sales),
                onCheckedChange = {
                    viewModel.onSwitchStateChange(NotificationGroup.Sales(it))
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
                defaultState = uiState.salesState
            )
            SwitchField(
                stringResource(id = R.string.label_new_arrivals),
                onCheckedChange = {
                    viewModel.onSwitchStateChange(NotificationGroup.NewArrivals(it))
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
                defaultState = uiState.newArrivalsState
            )
            SwitchField(
                stringResource(id = R.string.label_delivery_status_changes),
                onCheckedChange = {
                    viewModel.onSwitchStateChange(NotificationGroup.DeliveryStatusChanges(it))
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
                isSwitchEnabled = false,
                defaultState = uiState.deliveryStatusChangeState
            )

            CheckoutItemHeader(
                label = stringResource(R.string.label_help_center),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 20.dp)
            )
            ClickableField(
                stringResource(id = R.string.label_faq),
                null,
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.new_feature_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            ClickableField(
                stringResource(id = R.string.label_contact_us),
                null,
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.new_feature_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            ClickableField(
                stringResource(id = R.string.label_delivery),
                null,
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.new_feature_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            ClickableField(
                stringResource(id = R.string.label_return),
                null,
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.new_feature_message),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .padding(bottom = 30.dp)
            )
        }
    }
}

@Preview
@Composable
fun PersonalInformationCardPreview() {
    PersonalInformationCard(
        title = stringResource(id = R.string.label_name),
        subtitle = stringResource(id = R.string.label_name)
    )
}

@Composable
fun PersonalInformationCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
                .fillMaxWidth(),
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = nunitoSansFamily,
                color = colorResource(id = R.color.light_gray)
            )
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
            text = subtitle,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = nunitoSansFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.dark_gray)
            )
        )
    }
}

@Preview
@Composable
fun SwitchFieldPreview() {
    SwitchField(stringResource(id = R.string.label_sales), {})
}

@Composable
fun SwitchField(
    label: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSwitchEnabled: Boolean = true,
    defaultState: Boolean = false,
) {
    var checked by remember { mutableStateOf(defaultState) }
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.dark_gray)
                )
            )

            Switch(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 14.dp),
                checked = checked,
                enabled = isSwitchEnabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = colorResource(id = R.color.switch_enabled),
                    checkedBorderColor = colorResource(id = R.color.switch_enabled),
                    uncheckedBorderColor = colorResource(id = R.color.switch_disabled),
                ),
                onCheckedChange = {
                    checked = it
                    onCheckedChange(it)
                }
            )
        }
    }
}