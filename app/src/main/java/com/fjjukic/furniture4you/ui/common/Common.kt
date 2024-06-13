package com.fjjukic.furniture4you.ui.common

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.theme.gelatioFamily

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

fun showFeatureNotAvailable(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.label_new_feature),
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun ColumnScope.CombinedClickableText(
    startTextResId: Int,
    endTextResId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ClickableText(
        modifier = modifier.align(Alignment.CenterHorizontally),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = gelatioFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.color_light_gray),
                )
            ) {
                append(stringResource(id = startTextResId))
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = gelatioFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.color_medium_gray),
                )
            ) {
                append(stringResource(id = endTextResId).uppercase())
            }
        },
        onClick = onClick
    )
}

@Composable
fun DisableBackNavigation(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back navigation
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        onBackPressedDispatcher.addCallback(backCallback)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                backCallback.remove()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            backCallback.remove()
        }
    }
}