package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.api.*
import com.andback.pocketfridge.data.repository.barcode.ProductRemoteDataSource
import com.andback.pocketfridge.data.repository.barcode.ProductRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSource
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.category.CategoryRemoteDataSource
import com.andback.pocketfridge.data.repository.category.CategoryRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSource
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.like.LikeRemoteDataSource
import com.andback.pocketfridge.data.repository.like.LikeRemoteDataSourceImpl
import com.andback.pocketfridge.data.repository.notification.NotificationRemoteDataSource
import com.andback.pocketfridge.data.repository.notification.NotificationRemoteDataSourceImpl
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

    @Provides
    @Singleton
    fun provideFridgeRemoteDataSource(fridgeApi: FridgeApi): FridgeRemoteDataSource {
        return FridgeRemoteDataSourceImpl(fridgeApi)
    }

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(categoryApi: CategoryApi): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(categoryApi)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(barcodeApi: BarcodeApi): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(barcodeApi)
    }

    @Provides
    @Singleton
    fun provideRecipeRemoteDataSource(recipeApi: RecipeApi): RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun provideLikeRemoteDataSource(likeApi: LikeApi): LikeRemoteDataSource {
        return LikeRemoteDataSourceImpl(likeApi)
    }

    @Provides
    @Singleton
    fun provideNotificationDataSource(notificationApi: NotificationApi): NotificationRemoteDataSource {
        return NotificationRemoteDataSourceImpl(notificationApi)
    }
}