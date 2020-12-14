package com.rappi.testapp.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappi.testapp.core.data.models.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM Movie ORDER By localId")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteMovies()
}