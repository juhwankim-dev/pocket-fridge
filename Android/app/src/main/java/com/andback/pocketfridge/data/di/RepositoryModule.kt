package com.andback.pocketfridge.data.di

import android.content.Context
import com.andback.pocketfridge.data.repository.*
import com.andback.pocketfridge.data.repository.barcode.ProductRemoteDataSource
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSource
import com.andback.pocketfridge.data.repository.category.CategoryRemoteDataSource
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreLocalDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.data.repository.like.LikeRemoteDataSource
import com.andback.pocketfridge.data.repository.notification.NotificationRemoteDataSource
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideIngreRepository(ingreRemoteDataSource: IngreRemoteDataSource, ingreLocalDataSource: IngreLocalDataSource): IngreRepository {
        return IngreRepositoryImpl(ingreRemoteDataSource, ingreLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideFridgeRepository(fridgeRemoteDataSource: FridgeRemoteDataSource): FridgeRepository {
        return FridgeRepositoryImpl(fridgeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryRemoteDataSource: CategoryRemoteDataSource): CategoryRepository {
        return CategoryRepositoryImpl(categoryRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideBarcodeRepository(productRemoteDataSource: ProductRemoteDataSource): BarcodeRepository {
        return BarcodeRepositoryImpl(productRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeRemoteDataSource: RecipeRemoteDataSource): RecipeRepository {
        return RecipeRepositoryImpl(recipeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLikeRepository(likeRemoteDataSource: LikeRemoteDataSource): LikeRepository {
        return LikeRepositoryImpl(likeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationRemoteDataSource: NotificationRemoteDataSource): NotificationRepository {
        return NotificationRepositoryImpl(notificationRemoteDataSource)
    }
}