package com.example.greentrip.utils

import com.example.greentrip.models.LoginResponse

data class AuthState(
    val isLoading: Boolean = false,
    val status: String? = null,
    val userLogin: LoginResponse? = null
)
