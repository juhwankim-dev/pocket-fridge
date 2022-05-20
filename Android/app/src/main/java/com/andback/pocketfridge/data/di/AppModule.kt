package com.andback.pocketfridge.data.di

import android.content.Context
import com.andback.pocketfridge.data.infra.IngreExpiryNotiManagerImpl
import com.andback.pocketfridge.domain.infra.IngreExpiryNotiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideIngreNotiManager(@ApplicationContext context: Context): IngreExpiryNotiManager {
        return IngreExpiryNotiManagerImpl(context)
    }
}