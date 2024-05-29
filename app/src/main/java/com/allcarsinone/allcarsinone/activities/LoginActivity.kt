package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityLoginBinding
import com.allcarsinone.allcarsinone.dtos.LoginUserDto
import com.allcarsinone.allcarsinone.models.Token
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


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

        val sharedPrefences = DataUtils.getSharedPreferences(this)
        val token = sharedPrefences.getString("token", "")

        if(token != "") {
            openInitialPage()
        }

        viewBinding.loginCreateAccountTv.setOnClickListener {
            openRegisterForm()
        }

        viewBinding.btnLogin.setOnClickListener {
            login()
        }

    }

    fun login() {
        try {
            val loginDto = LoginUserDto(
                viewBinding.etEmail.text.toString(),
                viewBinding.etPassword.text.toString()
            )
            val userAPI = Globals.userAPI
            val call = userAPI.login(loginDto)

            call.enqueue(object : Callback<Token> {
                override fun onFailure(p0: Call<Token>, p1: Throwable) {
                    Toast.makeText(this@LoginActivity, p1.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(p0: Call<Token>, p1: Response<Token>) {
                    if (p1.isSuccessful) {
                        val sharedPreferences = DataUtils.getSharedPreferences(this@LoginActivity)
                        sharedPreferences.edit().putString("token", p1.body()!!.token).apply()
                        openInitialPage()
                    } else {
                        Toast.makeText(this@LoginActivity, JSONObject(p1.errorBody()?.string()).getString("message"), Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    fun openInitialPage() {
        val intent = Intent(this, InitialPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openRegisterForm() {
        val intent = Intent(this, RegisterActivity::class.java)

        registerFormRequest.launch(intent)

    }
}