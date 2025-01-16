package com.fjjukic.furniture4you.ui.setting

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.auth.enable_biometrics.EnableBiometricsScreen
import com.fjjukic.furniture4you.ui.checkout.CheckoutItemHeader
import com.fjjukic.furniture4you.ui.common.showFeatureNotAvailable
import com.fjjukic.furniture4you.ui.components.ClickableField
import com.fjjukic.furniture4you.ui.components.Toolbar
import com.fjjukic.furniture4you.ui.dialog.PasswordChangeDialog
import com.fjjukic.furniture4you.ui.dialog.PersonalInformationChangeDialog
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onBackClick = {})
}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    var passwordDialog by remember { mutableStateOf(false) }
    var personalInformationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.messageId) {
        state.messageId?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }

    if (personalInformationDialog) {
        PersonalInformationChangeDialog(
            state.personalInformation,
            onContinueClick = {
                personalInformationDialog = false
                viewModel.onEditClick(it)
            },
            onDismissClick = {
                personalInformationDialog = false
            }
        )
    }

    if (passwordDialog) {
        PasswordChangeDialog(
            state.password,
            onContinueClick = {
                passwordDialog = false
                viewModel.onPasswordChange(it)
            },
            onDismissClick = {
                passwordDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.nav_settings),
                startIconResId = R.drawable.ic_back,
                onStartActionClick = {
                    if (state.showBiometricsPrompt) {
                        viewModel.onBiometricsSkip()
                    } else onBackClick()
                },
                onEndActionClick = {},
                modifier = Modifier.background(colorResource(id = R.color.color_white))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.color_white))
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            CheckoutItemHeader(
                label = stringResource(R.string.title_personal_information),
                onEditClick = {
                    personalInformationDialog = true
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
            PersonalInformationCard(
                stringResource(id = R.string.label_name),
                state.personalInformation.name
            )
            PersonalInformationCard(
                stringResource(id = R.string.label_email),
                state.personalInformation.email
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
                state.password.replace(Regex("\\S"), "*")
            )

            if (state.biometricsAvailable) {
                CheckoutItemHeader(
                    label = stringResource(R.string.label_enable_biometrics),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .padding(horizontal = 20.dp)
                )

                SwitchField(
                    stringResource(id = R.string.label_use_biometrics),
                    onCheckedChange = {
                        viewModel.onBiometricsClick(it)
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 12.dp),
                    checked = state.biometricsEnabledState
                )
            }

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
                checked = state.salesState
            )
            SwitchField(
                stringResource(id = R.string.label_new_arrivals),
                onCheckedChange = {
                    viewModel.onSwitchStateChange(NotificationGroup.NewArrivals(it))
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
                checked = state.newArrivalsState
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
                checked = state.deliveryStatusChangeState
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
                    showFeatureNotAvailable(context)
                }
            )
            ClickableField(
                stringResource(id = R.string.label_contact_us),
                null,
                onClick = {
                    showFeatureNotAvailable(context)
                }
            )
            ClickableField(
                stringResource(id = R.string.label_delivery),
                null,
                onClick = {
                    showFeatureNotAvailable(context)
                }
            )
            ClickableField(
                stringResource(id = R.string.label_return),
                null,
                onClick = {
                    showFeatureNotAvailable(context)
                },
                modifier = Modifier
                    .padding(bottom = 30.dp)
            )
        }

        if (state.showBiometricsPrompt) {
            EnableBiometricsScreen(
                onSuccess = viewModel::onBiometricsSuccess,
                onSkipClick = viewModel::onBiometricsSkip,
                onExitClick = viewModel::onBiometricsSkip
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
            containerColor = colorResource(id = R.color.color_white)
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
                color = colorResource(id = R.color.color_light_gray)
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
                color = colorResource(id = R.color.color_dark_gray)
            )
        )
    }
}

@Preview
@Composable
fun SwitchFieldPreview() {
    SwitchField(label = stringResource(id = R.string.label_sales), onCheckedChange = {})
}

@Composable
fun SwitchField(
    label: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isTitle: Boolean = true,
    isSwitchEnabled: Boolean = true,
    checked: Boolean = false,
) {

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        color = colorResource(id = R.color.color_white),
        shadowElevation = if (isTitle) 2.dp else 0.dp
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
                    fontSize = if (isTitle) 16.sp else 14.sp,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.color_dark_gray)
                )
            )

            Switch(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 14.dp),
                checked = checked,
                enabled = isSwitchEnabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.color_white),
                    uncheckedThumbColor = colorResource(id = R.color.color_white),
                    checkedTrackColor = colorResource(id = R.color.bg_switch_enabled),
                    checkedBorderColor = colorResource(id = R.color.bg_switch_enabled),
                    uncheckedBorderColor = colorResource(id = R.color.bg_switch_disabled),
                ),
                onCheckedChange = {
                    if (isSwitchEnabled) {
                        onCheckedChange(it)
                    }
                }
            )
        }
    }
}