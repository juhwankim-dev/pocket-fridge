package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.api.FridgeApi
import com.andback.pocketfridge.data.api.IngreApi
import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.present.utils.RetrofitUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApiInterface(): UserApi {
        return RetrofitUtil.userService
    }

    @Provides
    @Singleton
    fun provideIngreApiInterface(): IngreApi  {
        return RetrofitUtil.ingreService
    }

    @Provides
    @Singleton
    fun provideFridgeApiInterface(): FridgeApi  {
        return RetrofitUtil.fridgeService
    }
}