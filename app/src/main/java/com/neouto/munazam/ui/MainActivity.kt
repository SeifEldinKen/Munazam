package com.neouto.munazam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neouto.munazam.R
import com.neouto.munazam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}