package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import com.allcarsinone.allcarsinone.dtos.InsertEditVehicleDto
import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests
import okhttp3.RequestBody
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

        val itemsRole = arrayOf("User", "Stand")
        val adapterRole = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsRole )
        viewBinding.registerRoleSPIN.setAdapter(adapterRole)

        viewBinding.NotificationsBackbuttonBtn.setOnClickListener {
            val intent = Intent(this, InitialPageActivity::class.java)
            startActivity(intent)
        }

    }
    private fun registerUserCallback(user: User?, errCode: Int) {
        if(user == null) {
            Toast.makeText(this, "Error: " + errCode.toString(), Toast.LENGTH_LONG).show()
            return;
        }

        val intent = Intent()
        intent.putExtra("email", user?.email)
        setResult(RESULT_OK, intent)
        finish()
    }
    fun validateDataFields() {
        val username = viewBinding.registerUsernameEt.text.toString()
        val name = viewBinding.registerNameEt.text.toString()
        val email = viewBinding.registerEmailEt.text.toString()
        val password = viewBinding.registerPasswordEt.text.toString()
        val confirmPassword = viewBinding.registerConfirmPasswordEt.text.toString()
        val roleId = viewBinding.registerRoleSPIN.selectedItemPosition + 1 // Select in base 0

        try {
            // 1- Admin, 2-Stand 3- Customer
            // By defaut user is always created as a Customer
            val user =
                RegisterUserDto(username, name, email, password, confirmPassword, "", "", "", roleId)
            DatabaseRequests.registerUser(user, ::registerUserCallback)
        } catch (ex:Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}