package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityEditVehicleBinding
import com.allcarsinone.allcarsinone.dtos.EditVehicleDto
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header


class EditVehicleActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityEditVehicleBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.btnInsertVehicle.setOnClickListener {
            validateDataFields()
        }
    }

    private fun processVehicle(token: String, body: EditVehicleDto) {

        val jsonString = Gson().toJson(body)
        val obj = JsonParser.parseString(jsonString).asJsonObject.toString()
        var rb = RequestBody.create(MediaType.parse("application/json; charset=uft-8"), obj)

        val call: Call<Vehicle> = if (body.id.toInt() > 0) {
            vehicleAPI.edit(body.id.toInt(), body)
        } else {
            vehicleAPI.register("Bearer $token", rb, mutableListOf())
        }

        call.enqueue(object : Callback<Vehicle> {
            override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                if (response.isSuccessful) {
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@EditVehicleActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@EditVehicleActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                //Toast.makeText(this@EditVehicleActivity, t.message, Toast.LENGTH_LONG).show()
                throw t
            }
        })
    }

    /*
    fun makeApiRequestWithToken(token: String) {
        val call = Globals.userAPI.validate("Bearer $token")

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    println("Success: ${apiResponse?.email}")
                } else {
                    println("Request failed with status: ${response.code()}")
                    Toast.makeText(this@EditVehicleActivity, "Request failed with status: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Request failed: ${t.message}")
                Toast.makeText(this@EditVehicleActivity, "Request failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    */


    private fun validateDataFields() {
        val sharedPrefs = DataUtils.getSharedPreferences(this@EditVehicleActivity)
        //val token = sharedPrefs.getString("token", "")
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwidXNlcm5hbWUiOiJhZG1pbnRlc3RlIiwibmFtZSI6IkFkbWluVGVzdGUiLCJlbWFpbCI6ImFkbWludGVzdGVAYWxsY2Fyc2lub25lLnB0IiwicGFzc3dvcmQiOiIkMmIkMTAkd0ZLOXJNb1YvQmFzcDJUeW13cUY5LkZjNmVmYmxwc0tUNFFoUUprc0QvTHY1a3Z2LnVtdU8iLCJhZGRyZXNzIjoiYWtkc3AiLCJjaXR5IjoiYmFyY2Vsb3MiLCJwb3N0YWxjb2RlIjoiMTAtMiIsIm1vYmlsZXBob25lIjoiMTIzNDU2NzgiLCJwaG90byI6bnVsbCwicm9sZV9pZCI6MSwiaWF0IjoxNzE3NjY2NTEyLCJleHAiOjE3MTc2NzM3MTJ9.jDv9CMiuaq9mtOpWcRJN31DtStCVf7JmvpUm1cKyOB4"

        //makeApiRequestWithToken(token)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token is missing", Toast.LENGTH_LONG).show()
            return
        }

        val id = 0
        val standid = 1
        val brandid = 1 //viewBinding.editVehicleBrandEt.text.toString().toInt()
        val gastypeid = 1
        val model = "XPTO" //viewBinding.editVehicleBrandEt.text.toString()
        val year = 2020
        val mileage = 120000
        val price = 12.6 //viewBinding.editVehiclePriceEt.text.toString().toDouble()
        val availability = true
        val description = "Muito bom"
        val brandname = ""
        val gastypename = ""
        val photos = arrayOf("F1", "F2")
        val consume = 10.5

        try {
            val vehicle = EditVehicleDto(
                standid, brandid, gastypeid, model, year, mileage, price, availability, description, brandname, gastypename, id, consume
            )
            processVehicle(token, vehicle)
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
