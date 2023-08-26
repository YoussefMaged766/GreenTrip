package com.example.greentrip.utils

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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
    private var countdownTimer: CountDownTimer? = null
    private val timerDurationMillis: Long = 10 * 1000 // 30 minutes in milliseconds

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
        if (countdownTimer == null) {
            countdownTimer = object : CountDownTimer(timerDurationMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.e("onTick: ", (millisUntilFinished / 1000).toString())
                }

                override fun onFinish() {
                    val intent = Intent("CountdownFinished")
                    intent.putExtra("ID_KEY", id)
                    LocalBroadcastManager.getInstance(this@CountdownService).sendBroadcast(intent)

                    Log.e("onTick: ", "finish")


                }
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }


}