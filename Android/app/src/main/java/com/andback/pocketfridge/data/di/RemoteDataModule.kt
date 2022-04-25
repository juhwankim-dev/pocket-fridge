package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.api.IngreApi
import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userAPi: UserApi): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPi)
    }

    @Provides
    @Singleton
    fun provideIngreRemoteDataSource(ingreApi: IngreApi): IngreRemoteDataSource {
        return IngreRemoteDataSourceImpl(ingreApi)
    }
}