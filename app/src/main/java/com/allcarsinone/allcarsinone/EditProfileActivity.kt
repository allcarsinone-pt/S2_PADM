package com.allcarsinone.allcarsinone

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.editprofileEditBtn.setOnClickListener {
            validateDataFields()
        }
    }

    fun validateDataFields() {
        val username = viewBinding.editprofileUsernameEt.text.toString()
        val name = viewBinding.editprofileNameEt.text.toString()
        val email = viewBinding.editprofileEmailEt.text.toString()
        val password = viewBinding.editprofilePasswordEt.text.toString()
        val confirmPassword = viewBinding.editprofileConfirmPasswordTv.text.toString()

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