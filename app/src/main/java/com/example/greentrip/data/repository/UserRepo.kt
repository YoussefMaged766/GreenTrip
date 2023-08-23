package com.example.greentrip.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.greentrip.models.BookingModel
import com.example.greentrip.models.LoginResponse
import com.example.greentrip.utils.Status
import com.example.greentrip.utils.WebServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class UserRepo @Inject constructor(private val webServices: WebServices) {
    private val gson = Gson()

    fun getProfile() = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getProfile()
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    suspend fun updateProfile(
        fileUri: Uri?,
        fileRealPath: String?,
        name: String,
        phone: String,
        email: String,
        ctx: Context
    ) = flow {
        emit(Status.Loading)
        try {
            if (fileUri != null && fileRealPath != null) {
                val fileToSend = prepareFilePart("avatar", fileRealPath, fileUri, ctx)
                val nameRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val phoneRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), phone)
                val emailRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val response = webServices.updateProfile(
                    image = fileToSend,
                    name = nameRequestBody,
                    phone = phoneRequestBody,
                    email = emailRequestBody
                )
                emit(Status.Success(response))
            } else {
                val nameRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val phoneRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), phone)
                val emailRequestBody: RequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), email)

                val response = webServices.updateProfile(
                    name = nameRequestBody,
                    phone = phoneRequestBody,
                    email = emailRequestBody,
                )
                emit(Status.Success(response))
            }


        } catch (e: Exception) {
            emit(Status.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    private fun prepareFilePart(
        partName: String,
        fileRealPath: String,
        fileUri: Uri,
        ctx: Context
    ): MultipartBody.Part {
        val file: File = File(fileRealPath)
        val requestFile: RequestBody = RequestBody.create(
            ctx.contentResolver.getType(fileUri)!!.toMediaTypeOrNull(), file
        )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


    fun getAllPoints() = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getAllPoints()
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getSpecificPoint(id:String) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getSpecificPoints(id)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getActivity(id:String) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getAllActivities(id)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)


    fun booking(booking:BookingModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.booking(booking)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)
    fun addPoint(booking:BookingModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.addPoint(booking)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun createVoucher(booking:BookingModel) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.createVoucher(booking)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getSpecificActivity(id:String) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getSpecificActivity(id)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getAllRewards() = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getAllRewards()
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun getSpecificReward(id:String) = flow {

        try {
            emit(Status.Loading)

            val response = webServices.getSpecificReward(id)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error(errorResponse?.message.toString()))
                }

                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error(e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)



}