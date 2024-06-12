package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.models.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityRegisterBinding
    private val usersAPI by lazy { Globals.userAPI }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.registerRegisterBtn.setOnClickListener {
            validateDataFields()
        }
    }

    fun registerUser(body: RegisterUserDto) {
        val call = usersAPI.register(body)

        call.enqueue(object: Callback<User> {
            override fun onResponse(p0: Call<User>, p1: Response<User>) {
                if(p1.isSuccessful) {
                    val intent = Intent()
                    intent.putExtra("email", p1.body()!!.email)
                    setResult(RESULT_OK, intent)
                    finish()
                }
                else {
                    when(p1.code()) {
                        400 -> {
                            Toast.makeText(this@RegisterActivity, JSONObject(p1.errorBody()?.string()).getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Toast.makeText(this@RegisterActivity, p1.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun validateDataFields() {
        val username = viewBinding.registerUsernameEt.text.toString()
        val name = viewBinding.registerNameEt.text.toString()
        val email = viewBinding.registerEmailEt.text.toString()
        val password = viewBinding.registerPasswordEt.text.toString()
        val confirmPassword = viewBinding.registerConfirmPasswordEt.text.toString()

        try {
            // 1- Admin, 2-Stand 3- Customer
            // By defaut user is always created as a Customer
            val user =
                RegisterUserDto(username, name, email, password, confirmPassword, "", "", "", 2)
            registerUser(user)
        } catch (ex:Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }

}