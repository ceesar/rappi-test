package com.rappi.testapp.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappi.testapp.core.data.models.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE label = :label")
    suspend fun remoteKeyByLabel(label: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun deleteByQuery(label: String)
}