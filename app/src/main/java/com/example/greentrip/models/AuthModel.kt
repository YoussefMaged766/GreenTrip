package com.example.greentrip.models

data class AuthModel(
    val email: String?=null,
    val password: String?=null,
    val name: String?=null,
    val passwordConfirm: String?=null,
)