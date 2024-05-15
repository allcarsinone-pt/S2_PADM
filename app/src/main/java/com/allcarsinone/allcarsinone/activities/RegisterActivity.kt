package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.registerRegisterBtn.setOnClickListener {
            validateDataFields()
        }
    }

    fun validateDataFields() {
        val username = viewBinding.registerUsernameEt.text.toString()
        val name = viewBinding.registerNameEt.text.toString()
        val email = viewBinding.registerEmailEt.text.toString()
        val password = viewBinding.registerPasswordEt.text.toString()
        val confirmPassword = viewBinding.registerConfirmPasswordEt.text.toString()

        val pattern = Patterns.EMAIL_ADDRESS.matcher(email)

        if(username.length < 5)
            Toast.makeText(this, "Username must be at least 5 characters", Toast.LENGTH_SHORT).show()
        if(password.length < 8)
            Toast.makeText(this, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show()
        if(confirmPassword != password)
            Toast.makeText(this, "Password and Confirmation Password doesn't match.", Toast.LENGTH_SHORT).show()
        if(!(pattern.matches()))
                Toast.makeText(this, "Email is not valid. Ex: example@allcarsinone.pt", Toast.LENGTH_SHORT).show()
        if(name.length < 5)
            Toast.makeText(this, "Name must be at least 5 characters.", Toast.LENGTH_SHORT).show()
    }

}