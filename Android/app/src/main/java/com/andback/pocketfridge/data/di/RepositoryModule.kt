package com.andback.pocketfridge.data.di

import com.andback.pocketfridge.data.repository.*
import com.andback.pocketfridge.data.repository.barcode.ProductRemoteDataSource
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSource
import com.andback.pocketfridge.data.repository.category.CategoryRemoteDataSource
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.*
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
}