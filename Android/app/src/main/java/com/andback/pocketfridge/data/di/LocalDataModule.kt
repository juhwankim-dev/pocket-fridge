package com.andback.pocketfridge.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andback.pocketfridge.data.db.AppDatabase
import com.andback.pocketfridge.data.db.dao.IngreDao
import com.andback.pocketfridge.data.repository.ingredient.IngreLocalDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreLocalDataSourceImpl
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSourceImpl
import com.andback.pocketfridge.present.config.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideIngreDao(appDatabase: AppDatabase): IngreDao {
        return appDatabase.ingreDao()
    }

    @Provides
    @Singleton
    fun provideIngreLocalDataSource(ingreDao: IngreDao): IngreLocalDataSource {
        return IngreLocalDataSourceImpl(ingreDao)
    }

}