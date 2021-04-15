package com.kaps.siliconstackkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kaps.siliconstackkotlin.R
import com.kaps.siliconstackkotlin.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        viewModel.context = this
        viewModel.onProgress()
    }
}