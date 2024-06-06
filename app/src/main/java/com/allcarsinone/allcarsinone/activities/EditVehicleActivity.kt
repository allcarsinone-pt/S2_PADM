package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityEditVehicleBinding
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.dtos.RegisterVehicleDto
import com.google.gson.annotations.SerializedName
import com.allcarsinone.allcarsinone.api.interfaces.VehiclesAPI
import com.allcarsinone.allcarsinone.dtos.EditVehicleDto
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditVehicleActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityEditVehicleBinding
    private val vehiclesAPI by lazy { Globals.vehiclesAPI }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.btnInsertVehicle.setOnClickListener {
            validateDataFields()
        }
    }

    val sharedPrefs = DataUtils.getSharedPreferences(this@EditVehicleActivity)
    val token = sharedPrefs.getString("token", "")

    fun edit(body:EditVehicleDto) {
        val call = vehiclesAPI.edit(body.id, body)
/*
        call.enqueue(object: Callback<Vehicle> {
            override fun onResponse(p0: Call<Vehicle>, p1: Response<Vehicle>) {
                if(p1.isSuccessful) {
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }
                else {
                    when(p1.code()) {
                        400 -> {
                            Toast.makeText(this@EditVehicleActivity, JSONObject(p1.errorBody()?.string()).getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Toast.makeText(this@EditVehicleActivity, p1.message, Toast.LENGTH_LONG).show()
            }
        })
        */
    }

    fun validateDataFields() {

        //GET  Bear = token   /proxy-auth/auth

        val standid =1
        val brandid = viewBinding.editVehicleBrandEt.text.toString().toInt()
        val gastypeid = 1
        val model = viewBinding.editVehicleBrandEt.text.toString()
        val year = 2020
        val mileage = 120000
        val price = viewBinding.editVehiclePriceEt.text.toString().toDouble()
        val availability = true
        val description = "Muito bom"
        val brandname = ""
        val gastypename = ""
        val photos = arrayOf("F1", "F2")
        val consume = 10.5

        try {
            val vehicle =
                RegisterVehicleDto( standid, brandid, gastypeid, model, year, mileage, price, availability, description, brandname, gastypename, photos, consume )
            //register(vehicle)
        } catch (ex:Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}