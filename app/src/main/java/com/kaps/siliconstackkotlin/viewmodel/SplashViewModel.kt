package com.kaps.siliconstackkotlin.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.kaps.siliconstackkotlin.view.MainActivity
import com.kaps.siliconstackkotlin.view.SplashActivity


class SplashViewModel : ViewModel() {

    lateinit var context: Context

    fun onProgress() {
        Thread(Runnable {
            for (i in 1..100) {
                if (i == 100) {
                    onSuccess()
                }
                Thread.sleep(10)
            }
        }).start()
    }

    fun onSuccess() {
        context.startActivity(Intent(context, MainActivity::class.java))
        val activity : SplashActivity = context as SplashActivity
        activity.finish()
    }
}