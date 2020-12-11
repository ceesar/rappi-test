package com.rappi.testapp.core.data.api.models

data class Resource<out T>(val status: Int, val data: T? = null, val message: String? = null) {
    companion object {
        const val SUCCESS = 1
        const val ERROR = 2
        const val LOADING = 3

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(ERROR, message = msg)
        }

        fun loading(): Resource<Any> {
            return Resource(LOADING)
        }
    }
}