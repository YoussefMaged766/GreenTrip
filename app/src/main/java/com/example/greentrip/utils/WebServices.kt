package com.example.greentrip.utils

import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.authModel
import retrofit2.http.Body
import retrofit2.http.POST

interface WebServices {

    @POST("api/v1/users/login")
    suspend fun loginUser(@Body user: authModel): LoginResponse

    @POST("api/v1/users/signup")
    suspend fun registerUser(@Body user: authModel): LoginResponse

}