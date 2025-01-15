package com.fjjukic.furniture4you.ui.auth.enable_biometrics

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.auth.login.BiometricData
import com.fjjukic.furniture4you.ui.common.crypto.BiometricsHelper
import com.fjjukic.furniture4you.ui.common.repository.BiometricsAvailability
import com.fjjukic.furniture4you.ui.common.utils.findActivity
import com.fjjukic.furniture4you.ui.common.utils.openBiometricsSettings
import com.fjjukic.furniture4you.ui.components.DotsProgressIndicator
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Composable
fun EnableBiometricsScreen(
    onSuccess: () -> Unit,
    onSkipClick: () -> Unit,
    onExitClick: () -> Unit,
    viewModel: EnableBiometricsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.messageResId) {
        if (state.messageResId != null) {
            Toast.makeText(context, state.messageResId, Toast.LENGTH_SHORT).show()
            viewModel.onMessageShown()
        }
    }
    LaunchedEffect(state.biometricsActivated) {
        if (state.biometricsActivated == true) {
            onSuccess()
            viewModel.resetState()
        }
    }
    LaunchedEffect(state.biometricsFailed) {
        if (state.biometricsFailed == true) {
            onSkipClick()
            viewModel.resetState()
        }
    }

    EnableBiometricsItem(
        biometricData = state.biometricData,
        onBiometricActivationSuccess = viewModel::onBiometricActivationSuccess,
        onExitClick = onExitClick,
        onSkipClick = onSkipClick,
        biometricsAvailability = state.biometricsAvailability
    )
}

@Composable
fun EnableBiometricsItem(
    biometricData: BiometricData? = null,
    onBiometricActivationSuccess: (authResult: BiometricPrompt.AuthenticationResult) -> Unit,
    onExitClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    biometricsAvailability: BiometricsAvailability = BiometricsAvailability.Checking
) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.color_white))
    ) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = 40.dp,
                    horizontal = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(72.dp))

            Text(
                text = stringResource(R.string.label_enable_biometrics),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.color_medium_gray),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_biometrics_explanation),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = nunitoSansFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.color_gray),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_fingerprint),
                contentDescription = "Biometrics logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(160.dp)
                    .padding(16.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.color_medium_gray)),
            )

            Spacer(Modifier.weight(1f))

            val activity = LocalContext.current.findActivity()

            when (biometricsAvailability) {
                BiometricsAvailability.Checking -> {
                    DotsProgressIndicator(modifier = Modifier.padding(56.dp))
                }

                BiometricsAvailability.Available -> {
                    BottomActions(
                        onFirstActionClick = {
                            if (biometricData != null) {
                                BiometricsHelper.registerUserBiometrics(
                                    activity,
                                    biometricData.cipher,
                                    onSuccess = onBiometricActivationSuccess,
                                )
                            }
                        },
                        onSecondActionClick = onSkipClick
                    )
                }

                BiometricsAvailability.NotAvailable -> {
                    BottomActions(
                        onFirstActionClick = {},
                        onSecondActionClick = onExitClick,
                        firstActionTitleResId = stringResource(id = R.string.label_setup_biometrics),
                        titleResId = stringResource(R.string.error_biometrics_not_available)
                    )
                }

                BiometricsAvailability.NotEnabled -> {
                    BottomActions(
                        onFirstActionClick = {
                            activity.openBiometricsSettings()
                        },
                        onSecondActionClick = onExitClick,
                        firstActionTitleResId = stringResource(id = R.string.btn_open_settings),
                        titleResId = stringResource(R.string.label_biometrics_enable_explanation)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomActionsPreview() {
    BottomActions(
        onFirstActionClick = {},
        onSecondActionClick = {},
        titleResId = stringResource(id = R.string.label_biometrics_enable_explanation)
    )
}

@Composable
fun BottomActions(
    onFirstActionClick: () -> Unit,
    onSecondActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    firstActionTitleResId: String = stringResource(R.string.label_setup_biometrics),
    titleResId: String? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (titleResId != null) {
            Text(
                text = titleResId,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.color_dark_gray),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )
        }
        TextButton(
            onClick = {
                onFirstActionClick()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.color_dark_gray)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = firstActionTitleResId,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.color_white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        TextButton(
            onClick = {
                onSecondActionClick()
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = stringResource(R.string.label_skip_this_step),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.color_gray),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EnableBiometricsScreen_Preview() {
    EnableBiometricsItem(
        onBiometricActivationSuccess = {},
        biometricsAvailability = BiometricsAvailability.Available
    )
}

@Composable
@Preview(showBackground = true)
fun EnableBiometricsScreen_Not_Enrolled_Preview() {
    EnableBiometricsItem(
        onBiometricActivationSuccess = {},
        biometricsAvailability = BiometricsAvailability.NotEnabled
    )
}


@Composable
@Preview(showBackground = true)
fun EnableBiometricsScreen_Not_Available_Preview() {
    EnableBiometricsItem(
        onBiometricActivationSuccess = {},
        biometricsAvailability = BiometricsAvailability.NotAvailable
    )
}

@Composable
@Preview(showBackground = true)
fun EnableBiometricsScreen_Loading_Preview() {
    EnableBiometricsItem(
        onBiometricActivationSuccess = {},
        biometricsAvailability = BiometricsAvailability.Checking
    )
}