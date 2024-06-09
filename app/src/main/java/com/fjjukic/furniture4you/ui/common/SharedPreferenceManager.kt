package com.fjjukic.furniture4you.ui.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FurniturePrefs", Context.MODE_PRIVATE)

}

