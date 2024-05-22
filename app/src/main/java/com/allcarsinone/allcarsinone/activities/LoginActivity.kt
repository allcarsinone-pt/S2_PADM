package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityLoginBinding
    private var registerFormRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK) {
            viewBinding.etEmail.setText(it.data!!.getStringExtra("email"))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.loginCreateAccountTv.setOnClickListener {
            openRegisterForm()
        }

    }

    fun openRegisterForm() {
        val intent = Intent(this, RegisterActivity::class.java)

        registerFormRequest.launch(intent)

    }
}