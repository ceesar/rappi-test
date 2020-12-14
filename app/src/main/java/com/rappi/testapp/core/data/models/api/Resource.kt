package com.rappi.testapp.core.data.models.api

data class Resource<out T>(val status: Int, val data: T? = null, val message: String? = null) {
    companion object {
        const val SUCCESS = 1
        const val ERROR = 2
        const val LOADING = 3

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data)
        }

        fun <T> error(msg: String? = null): Resource<T> {
            return Resource(ERROR, message = msg)
        }

        fun loading(): Resource<Any> {
            return Resource(LOADING)
        }
    }
}