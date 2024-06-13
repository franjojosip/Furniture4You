package com.fjjukic.furniture4you.ui.common.di

import com.fjjukic.furniture4you.ui.common.crypto.CryptoManager
import com.fjjukic.furniture4you.ui.common.crypto.CryptoManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun getCryptoManager(): CryptoManager {
        return CryptoManagerImpl()
    }
}