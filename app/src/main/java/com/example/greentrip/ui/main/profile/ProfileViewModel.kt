package com.example.greentrip.ui.main.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.UserRepo
import com.example.greentrip.models.AuthModel
import com.example.greentrip.utils.AuthState
import com.example.greentrip.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asSharedFlow()

    private val _state1 = MutableStateFlow(AuthState())
    val state1 = _state1.asSharedFlow()

    private val _fileName = MutableLiveData("")

    // new added
    val fileName: LiveData<String>
        get() = _fileName

    // new added
    fun setFileName(name: String) {
        _fileName.value = name
    }

    init {
        getProfile()
    }

    private fun getProfile() =
        viewModelScope.launch {
            userRepo.getProfile().collect {
                when (it) {
                    is Status.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Status.Success -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.data.status.toString(),
                            profile = it.data
                        )

                    }

                    is Status.Error -> {

                        _state.value = _state.value.copy(
                            isLoading = false,
                            status = it.message,

                            )
                    }

                    else -> {}
                }
            }
        }

        fun editProfile(
            fileUri: Uri? = null, fileRealPath: String? = null,
            name: String,
            email: String,
            ctx: Context,
            phone: String
        ) =
            viewModelScope.launch {
                userRepo.updateProfile(
                    fileUri = fileUri,
                    fileRealPath = fileRealPath,
                    name = name,
                    email = email,
                    ctx = ctx,
                    phone = phone
                ).collect {
                    when (it) {
                        is Status.Loading -> {
                            _state1.value = _state1.value.copy(isLoading = true)
                        }

                        is Status.Success -> {

                            _state1.value = _state1.value.copy(
                                isLoading = false,
                                status = it.data.status.toString(),

                            )

                        }

                        is Status.Error -> {

                            _state1.value = _state1.value.copy(
                                isLoading = false,
                                status = it.message,

                                )
                        }

                        else -> {}
                    }
                }
            }
}