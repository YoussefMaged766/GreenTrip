package com.example.greentrip.data.repository

import android.util.Log
import com.example.greentrip.models.LoginResponse
import com.example.greentrip.models.authModel
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
    fun loginUser(user: authModel) = flow {

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

}