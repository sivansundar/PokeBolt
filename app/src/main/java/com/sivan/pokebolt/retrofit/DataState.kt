package com.sivan.pokebolt.retrofit

sealed class DataState<out R> {

    data class Success<out T>(val data : T) :DataState<T>()

    data class Error(val exception: Exception) :DataState<Nothing>() {
        val message = exception.message
    }

    object Loading: DataState<Nothing>()
}