package com.example.greentrip.utils


sealed class Status<out T>{
    data class Success<T>(val data :T): Status<T>()
    data class Error(val message: String): Status<Nothing>()
    object Loading: Status<Nothing>()
}