package com.rappi.testapp.core.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rappi.testapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    fun showLoader() {

    }

    fun hideLoader() {

    }
}