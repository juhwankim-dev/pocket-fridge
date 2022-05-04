package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.api.*
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

    @Provides
    @Singleton
    fun provideCategoryApiInterface(): CategoryApi {
        return RetrofitUtil.categoryService
    }

    @Provides
    @Singleton
    fun provideRecipeApiInterface(): RecipeApi {
        return RetrofitUtil.recipeService
    }

    fun provideBarcodeApiInterface(): BarcodeApi {
        return RetrofitUtil.barcodeService
    }
}