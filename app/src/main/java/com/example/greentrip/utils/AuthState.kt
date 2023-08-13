package com.example.greentrip.utils

import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.UserResponse

data class AuthState(
    val isLoading: Boolean = false,
    val status: String? = null,
    val userLogin: LoginResponse? = null,
    val profile :UserResponse? = null
)
