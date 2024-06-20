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
import com.allcarsinone.allcarsinone.R
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
    var roleid:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.editProfileBackbuttonBtn.setOnClickListener {
            finish()
        }

        viewBinding.editprofileEditBtn.setOnClickListener {
            validateDataFields()
        }
        val username = intent?.getStringExtra("username")
        roleid = intent?.getIntExtra("roleid", 0)!!
        if(roleid == 0) {
            finish()
            return
        }
        if(username.isNullOrEmpty()) {
            finish()
            return
        }
        val call = usersAPI.getUser(username)

        call.enqueue(object: Callback<User> {
            override fun onResponse(p0: Call<User>, p1: Response<User>) {
                if(p1.isSuccessful) {
                    viewBinding.editprofileUsernameEt.setText(p1.body()!!.username)
                    viewBinding.editprofileNameEt.setText(p1.body()!!.name)
                    viewBinding.editprofileEmailEt.setText(p1.body()!!.email)
                    viewBinding.editprofileAddressEt.setText(p1.body()!!.address)
                    viewBinding.editprofileMobilephoneEt.setText(p1.body()!!.mobilephone)
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

    fun editUser(body: EditUserDto) {
        val call = usersAPI.edit(body.username, body)

        call.enqueue(object: Callback<User> {
            override fun onResponse(p0: Call<User>, p1: Response<User>) {
                if(p1.isSuccessful) {

                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
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
        var mobilephone : String? = viewBinding.editprofileMobilephoneEt.text.toString()
        var address : String? = viewBinding.editprofileAddressEt.text.toString()
        val roleid =
        try {
            mobilephone = if(mobilephone!!.length > 8) mobilephone else null
            address = if(address!!.length > 3) address else null
            val user = EditUserDto(username, name, email, mobilephone, address,"", roleid)
            editUser(user)
        } catch (ex:Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}