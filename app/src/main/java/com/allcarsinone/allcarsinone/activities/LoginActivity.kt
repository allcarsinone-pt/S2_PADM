package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

    }
}