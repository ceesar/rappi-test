package com.rappi.testapp.core.data.repository

import android.util.Log
import com.rappi.testapp.core.data.models.api.Resource
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <R> makeRequest(request: suspend () -> Response<R>): Resource<R> {
        try {
            request.invoke().run {
                return if (isSuccessful) {
                    Resource.success(body())
                } else {
                    Resource.error("Code: ${code()}, ${errorBody()} ")
                }
            }
        } catch (exception: Exception) {
            Log.e(this::class.simpleName, "Error executing service", exception)
            return Resource.error()
        }

    }
}