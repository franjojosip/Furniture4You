package com.fjjukic.furniture4you.ui.common.di

import android.content.Context
import com.fjjukic.furniture4you.ui.common.crypto.AeadManager
import com.fjjukic.furniture4you.ui.common.crypto.CryptoManagerImpl
import com.fjjukic.furniture4you.ui.common.crypto.EncryptedPrefsManager
import com.fjjukic.furniture4you.ui.common.repository.MainRepository
import com.fjjukic.furniture4you.ui.common.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        @ApplicationContext appContext: Context,
        cryptoManager: CryptoManagerImpl,
        encryptedPrefsManager: EncryptedPrefsManager,
        aeadManager: AeadManager
    ): MainRepository {
        return MainRepositoryImpl(appContext, cryptoManager, encryptedPrefsManager, aeadManager)
    }
}