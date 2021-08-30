package com.example.tada.di

import android.content.Context
import androidx.room.Room
import com.example.tada.data.clients.CategoryDatabaseClient
import com.example.tada.data.room.CategoryDao
import com.example.tada.data.room.TadaDatabase
import com.example.tada.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTadaDatabase(@ApplicationContext appContext: Context): TadaDatabase {
        return Room.databaseBuilder(
            appContext,
            TadaDatabase::class.java,
            TadaDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseClient(tadaDatabase: TadaDatabase): CategoryDatabaseClient {
        return CategoryDatabaseClient(tadaDatabase)
    }

    @Provides
    @Singleton
    fun provideCategoryDao(tadaDatabase: TadaDatabase): CategoryDao {
        return tadaDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(categoryDatabaseClient: CategoryDatabaseClient): TaskRepository {
        return TaskRepository(categoryDatabaseClient)
    }


}