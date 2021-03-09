package com.example.mysimplecoindeck.utils

// Recommend by Google to wrap around our network responses
// sealed class is like a abstract call but we can define which classes are allowed to inherit from this sealed class
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}