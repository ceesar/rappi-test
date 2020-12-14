package com.rappi.testapp.core.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.rappi.testapp.core.data.db.MovieDao
import com.rappi.testapp.core.data.db.MovieDatabase
import com.rappi.testapp.core.data.db.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideRemoteKeyDao(movieDatabase: MovieDatabase): RemoteKeyDao {
        return movieDatabase.remoteKeysDao()
    }
}