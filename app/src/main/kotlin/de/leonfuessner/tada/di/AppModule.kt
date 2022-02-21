package de.leonfuessner.tada.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.leonfuessner.tada.data.clients.CategoryDatabaseClient
import de.leonfuessner.tada.data.room.CategoryDao
import de.leonfuessner.tada.data.room.TadaDatabase
import de.leonfuessner.tada.repository.TaskRepository
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