package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityEditProfileBinding
import com.allcarsinone.allcarsinone.dtos.EditUserDto
import com.allcarsinone.allcarsinone.models.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityEditProfileBinding
    private val usersAPI by lazy { Globals.userAPI }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.editprofileEditBtn.setOnClickListener {
            validateDataFields()
        }
    }

    fun editUser(body: EditUserDto) {
        val call = usersAPI.edit(body.username, body)

        call.enqueue(object: Callback<User> {
            override fun onResponse(p0: Call<User>, p1: Response<User>) {
                if(p1.isSuccessful) {
                    Toast.makeText(this@EditProfileActivity,
                        "User updated with success!",
                        Toast.LENGTH_LONG).show()
                }
                else {
                    when(p1.code()) {
                        400 -> {
                            Toast.makeText(this@EditProfileActivity,
                                JSONObject(p1.errorBody()?.string()).getString("message"),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Toast.makeText(this@EditProfileActivity, p1.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validateDataFields() {
        val username = viewBinding.editprofileUsernameEt.text.toString()
        val name = viewBinding.editprofileNameEt.text.toString()
        val email = viewBinding.editprofileEmailEt.text.toString()
        val password = viewBinding.editprofilePasswordEt.text.toString()
        val confirmPassword = viewBinding.editprofileConfirmPasswordTv.text.toString()

        try {
            val user = EditUserDto(username, name, email, password, confirmPassword)
            editUser(user)
        } catch (ex:Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}