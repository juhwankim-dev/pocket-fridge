package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.repository.FridgeRepositoryImpl
import com.andback.pocketfridge.data.repository.IngreRepositoryImpl
import com.andback.pocketfridge.data.repository.UserRepositoryImpl
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.FridgeRepository
import com.andback.pocketfridge.domain.repository.IngreRepository
import com.andback.pocketfridge.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideIngreRepository(ingreRemoteDataSource: IngreRemoteDataSource): IngreRepository {
        return IngreRepositoryImpl(ingreRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFridgeRepository(fridgeRemoteDataSource: FridgeRemoteDataSource): FridgeRepository {
        return FridgeRepositoryImpl(fridgeRemoteDataSource)
    }
}