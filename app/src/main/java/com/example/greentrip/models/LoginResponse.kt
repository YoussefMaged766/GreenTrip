package com.example.greentrip.models

data class LoginResponse(
    val data: Data?=null,
    val status: String?=null,
    val token: String?=null,
    val message: String?=null
)

data class Data(
    val user: User
)
data class User(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val email: String,
    val id: String,
    val name: String,
    val photo: String,
    val role: String
)