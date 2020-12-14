package com.rappi.testapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rappi.testapp.core.data.models.entity.Movie
import com.rappi.testapp.core.data.models.entity.RemoteKey

@Database(
    entities = [Movie::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {
        const val DATABASE_NAME = "movieDatabase"
    }
}