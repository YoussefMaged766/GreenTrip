package com.example.greentrip.data.repository

import android.util.Log
import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.AuthModel
import com.example.greentrip.utils.Status
import com.example.greentrip.utils.WebServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepo @Inject constructor(private val webServices: WebServices) {
    private val gson = Gson()
    fun loginUser(user: AuthModel) = flow {

        try {
            emit(Status.Loading)

                val response = webServices.loginUser(user)
                emit(Status.Success(response))

                Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.status.toString())
                    emit(Status.Error( errorResponse?.status.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun registerUser(user: AuthModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.registerUser(user)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.status.toString())
                    emit(Status.Error( errorResponse?.status.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun forgotPassword(user: AuthModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.forgotPassword(user)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.status.toString())
                    emit(Status.Error( errorResponse?.status.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun verifyCode(token: String) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.verifyRecoverCode(token)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.status.toString())
                    emit(Status.Error( errorResponse?.status.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun resetPassword(token: String , user: AuthModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.resetPassword( user, token)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.status.toString())
                    emit(Status.Error( errorResponse?.status.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

}