package com.example.movieapp.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status
) {

    class Success<T>(data: T) : Resource<T>(data = data, status = Status.SUCCESS)
    class Error<T>(
        val errorMessage: String,
        val errorType: ErrorType? = null
    ) : Resource<T>(message = errorMessage, status = Status.ERROR)
    class Loading<T> : Resource<T>(status = Status.LOADING)
    class Unspecified<T> : Resource<T>(status = Status.UNDEFINE)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    UNDEFINE
}

enum class ErrorType {
    NETWORK,
    HTTP,
    UNKNOWN
}