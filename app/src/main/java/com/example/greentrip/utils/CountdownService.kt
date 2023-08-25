package com.example.greentrip.utils

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.greentrip.data.repository.UserRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CountdownService() : Service() {
    @Inject
    lateinit var userRepo: UserRepo
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private var countdownTimer: CountDownTimer? = null
    private val timerDurationMillis: Long = 20 * 1000 // 30 minutes in milliseconds

    private val _stateDeleteVoucher = MutableStateFlow(AuthState())
    val stateDeleteVoucher = _stateDeleteVoucher.asSharedFlow()
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val id = intent?.getStringExtra("ID_KEY")
        if (!id.isNullOrEmpty()) {
            startCountdown(id)
        }
        return START_STICKY
    }

    private fun startCountdown(id: String) {
        countdownTimer = object : CountDownTimer(timerDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("onTick: ", (millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                serviceScope.launch {
                    deleteVoucher(id)
                }

                Log.e("onTick: ", "finish")
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }

    suspend fun deleteVoucher(id: String) =

        userRepo.deleteVoucher(id).collect {
            when (it) {
                is Status.Loading -> {
                    _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(isLoading = true)
                }

                is Status.Success -> {

                    _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(
                        isLoading = false,
                        status = it.data.status.toString(),
                        specificVoucher = it.data
                    )

                }

                is Status.Error -> {

                    _stateDeleteVoucher.value = _stateDeleteVoucher.value.copy(
                        isLoading = false,
                        status = it.message,

                        )
                }

                else -> {}
            }
        }

}