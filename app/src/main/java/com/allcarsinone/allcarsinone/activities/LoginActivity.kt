package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityLoginBinding
import com.allcarsinone.allcarsinone.dtos.LoginUserDto
import com.allcarsinone.allcarsinone.models.Token
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests
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

        // TODO: Para ativar o código abaixo é necessário validar o token antes

        val BuyIntention: Boolean = intent.getBooleanExtra("buyFlag", false) as Boolean

        val sharedPrefences = DataUtils.getSharedPreferences(this)
        val token = sharedPrefences.getString("token", "")
        if(token == "" && BuyIntention == false) {
            openInitialPage("Guest", 3)
        }
        val validationResult = AuthUtils.validateToken(this,token)
        if(validationResult.success) {
            openInitialPage(validationResult.username, validationResult.roleid)
        }
        viewBinding.loginCreateAccountTv.setOnClickListener {
            openRegisterForm()
        }
        viewBinding.btnLogin.setOnClickListener {
            login()
        }
        viewBinding.txtForgotpassword.setOnClickListener {
            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkAuthCallback(token: String?, errCode: Int){

        try {
            if(errCode == 400) {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
                return
            }

            val validationResult = AuthUtils.validateToken(this, token)

            if (!validationResult.success) {
                Toast.makeText(this, "Error validating token", Toast.LENGTH_LONG).show()
            }
            else {
                openInitialPage(validationResult.username, validationResult.roleid)
            }
        }
        catch (err: Exception) {
            Toast.makeText(this, err.message, Toast.LENGTH_LONG).show()
        }
        //DatabaseRequests.getLoggedUser(this, token, ::getLoggedUserCallback)
    }


    fun login() {
        try {
            val email = viewBinding.etEmail.text.toString()
            val password = viewBinding.etPassword.text.toString()
            DatabaseRequests.login(email, password, ::checkAuthCallback )
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
    fun openInitialPage(username: String, roleId: Int) {
        val intent = if(roleId == 1 || roleId == 3)
            Intent(this, InitialPageActivity::class.java)
        else
            Intent(this, InitialPageStandActivity::class.java)

        intent.putExtra("username", username)
        startActivity(intent)
    }
    fun openRegisterForm() {
        val intent = Intent(this, RegisterActivity::class.java)
        registerFormRequest.launch(intent)
    }
}