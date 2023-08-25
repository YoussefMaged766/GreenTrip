package com.example.greentrip.constants

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore



object Constants {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("save")

    const val BASEURL = "http://192.168.1.6:8080/"

    const val TOKEN = "token"

    lateinit var dialog: Dialog
    fun showCustomAlertDialog(
        activity: Activity,
        layout: Int,
        checkCancel: Boolean,
    ) {
        if (!activity.isFinishing && !activity.isDestroyed) {
            dialog = Dialog(activity)
            dialog.setContentView(layout)
            dialog.setCancelable(checkCancel)

            dialog.show()

        }
    }
    fun hideCustomAlertDialog() {
        try {
            if (::dialog.isInitialized && dialog.isShowing) {
                dialog.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("hideCustomAlertDialog: ", e.message.toString())
        }


    }

}