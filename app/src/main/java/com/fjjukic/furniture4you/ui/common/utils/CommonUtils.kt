package com.fjjukic.furniture4you.ui.common.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

fun Context.findActivity(): AppCompatActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Context.openBiometricsSettings() {
    val intent = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Intent(Settings.ACTION_BIOMETRIC_ENROLL)
        else -> Intent(Settings.ACTION_SECURITY_SETTINGS)
    }
    startActivity(intent)
}