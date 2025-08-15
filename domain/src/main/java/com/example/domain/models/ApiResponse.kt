package com.example.domain.models

sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int,
    ): ApiResponse<Nothing>()

    inline fun <R> map(crossinline transform: (value: T) -> R): ApiResponse<R> {
        return when(this) {
            is Failure -> Failure(errorMessage,code)
            Loading -> Loading
            is Success<T> -> Success(transform(this.data))
        }
    }
}