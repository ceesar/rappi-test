package com.rappi.testapp.core.data.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rappi.testapp.common.constants.BACKDROP_SIZE
import com.rappi.testapp.common.constants.BASE_IMAGE_URL
import com.rappi.testapp.common.constants.THUMBNAIL_SIZE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index("localId"), Index("id")])
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val localId: Int? = null,
    val id: Int,
    val title: String,
    val voteAverage: Float,
    val backdropPath: String?,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?
) : Parcelable {
    fun getFullPosterPath() : String {
        return BASE_IMAGE_URL + THUMBNAIL_SIZE + posterPath
    }

    fun getFullBackdropPath() : String {
        return BASE_IMAGE_URL + BACKDROP_SIZE + backdropPath
    }
}