package com.fjjukic.furniture4you.ui.common.di

import android.content.Context
import com.fjjukic.furniture4you.ui.common.crypto.AeadManager
import com.fjjukic.furniture4you.ui.common.crypto.EncryptedPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EncryptionModule {

    @Provides
    fun provideEncryptedPrefsManager(@ApplicationContext context: Context): EncryptedPrefsManager {
        return EncryptedPrefsManager(context)
    }

    @Provides
    fun provideAeadManager(@ApplicationContext context: Context): AeadManager {
        return AeadManager(context)
    }
}