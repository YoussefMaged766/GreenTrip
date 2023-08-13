package com.example.greentrip.utils

import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.AuthModel
import com.example.greentrip.models.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface WebServices {

    @POST("api/v1/users/login")
    suspend fun loginUser(@Body user: AuthModel): LoginResponse

    @POST("api/v1/users/signup")
    suspend fun registerUser(@Body user: AuthModel): LoginResponse

    @POST("api/v1/users/forgotPassword")
    suspend fun forgotPassword(@Body user: AuthModel): LoginResponse

    @GET("api/v1/users/resetPassword/verify/{token}")
    suspend fun verifyRecoverCode(@Path("token") token:String): LoginResponse

    @PATCH("api/v1/users/resetPassword/{token}")
    suspend fun resetPassword(@Body user: AuthModel, @Path("token") token:String ) : LoginResponse


    @GET("api/v1/users/me")
    suspend fun getProfile(): UserResponse

    @Multipart
    @PATCH("api/v1/users/updateUserData")
    suspend fun updateProfile(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part? = null,
        ): UserResponse

}