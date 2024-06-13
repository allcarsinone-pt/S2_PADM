package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityEditVehicleBinding
import com.allcarsinone.allcarsinone.dtos.InsertEditVehicleDto
import com.allcarsinone.allcarsinone.models.Brand
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InsertEditVehicleActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEditVehicleBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI }
    private val brandAPI by lazy { Globals.brandAPI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val standID = 1 // TODO: Obeter o standID
        val vehicleID = intent.getIntExtra("vehicleid", 0) as Int
        //TODO: Testar id

        if(vehicleID > 0)
            viewBinding.btnInsertVehicle.text = getString(R.string.edit)

        viewBinding.btnInsertVehicle.setOnClickListener {
            validateDataFields(vehicleID, standID)
        }
        viewBinding.editVehicleBackbuttonBtn.setOnClickListener {
            finish()
        }

        loadVehicle(vehicleID)
        loadBrands(0)
    }
    private fun fillVehicleForm(v: Vehicle) {
        viewBinding.editVehicleModelEt.setText(v.model)
        viewBinding.editVehicleGastypeEt.setText(v.gastypename)
        viewBinding.editVehicleYearEt.setText(v.year.toString())
        viewBinding.editVehicleMileageEt.setText(v.mileage.toString())
        viewBinding.editVehiclePriceEt.setText(v.price.toString())
        viewBinding.editVehicleAvailabilityEt.setChecked(v.availability)
        viewBinding.editVehicleDescriptionEt.setText(v.description)
        viewBinding.editVehicleConsumeEt.setText(v.consume.toString())
        viewBinding.editVehicleLocationEt.setText(v.location)
    }
    private fun fillBrandSelect(b: List<Brand>, selectedBrand: Int) {

        // TODO: Selecionar a ativa

        val brandNames = b.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, brandNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.editVehicleBrandEt.adapter = adapter
    }
    private fun loadBrands(selectedBrand: Int) {

        val call: Call<List<Brand>> = brandAPI.getAll()
        call.enqueue(object : Callback<List<Brand>> {
            override fun onResponse(call: Call<List<Brand>>, response: Response<List<Brand>>) {
                if (response.isSuccessful) {
                    response.body()?.let { brands ->
                        fillBrandSelect(brands, selectedBrand)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@InsertEditVehicleActivity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@InsertEditVehicleActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Brand>>, t: Throwable) {
                Toast.makeText(this@InsertEditVehicleActivity, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun loadVehicle(vehicleID: Int) {
        val call: Call<Vehicle> = vehicleAPI.getVehicle(vehicleID)
        call.enqueue(object : Callback<Vehicle> {
            override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                if (response.isSuccessful) {
                    response.body()?.let { vehicle ->
                        fillVehicleForm(vehicle)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@InsertEditVehicleActivity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@InsertEditVehicleActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                Toast.makeText(this@InsertEditVehicleActivity, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun processVehicle(token: String, body: InsertEditVehicleDto) {

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
                    val intent = Intent(this@InsertEditVehicleActivity, BookTestDriveActivity::class.java)
                    setResult(RESULT_OK, intent)
                    startActivity(intent)
                } else {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@InsertEditVehicleActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@InsertEditVehicleActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                Toast.makeText(this@InsertEditVehicleActivity, t.message, Toast.LENGTH_LONG).show()
                //throw t
            }
        })
    }

    private fun validateDataFields(vehicleId: Int, standId: Int) {
        val sharedPrefs = DataUtils.getSharedPreferences(this@InsertEditVehicleActivity)
        val token = sharedPrefs.getString("token", "")
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NSwidXNlcm5hbWUiOiJ6ZWNhZ2FsaGFvIiwibmFtZSI6IlN0YW5kIFplY2EgR2FsaGFvIiwiZW1haWwiOiJwYXVsb2Fzc2lzdGFudEBnbWFpbC5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRHR3IzdkNJZkdTMUlVZlhtdDlDMG1lRXhCYndXYnBramNjM3VhMGJ1YXlGaGhaeU8yNHA1NiIsImFkZHJlc3MiOiIiLCJjaXR5IjpudWxsLCJwb3N0YWxjb2RlIjoiIiwibW9iaWxlcGhvbmUiOiIiLCJwaG90byI6bnVsbCwicm9sZV9pZCI6MiwiaWF0IjoxNzE4MjAyNzAxLCJleHAiOjE3MTgyMDk5MDF9.bMcZHpaIBBp45ov-jAvF4Yc09gY1qt8GPXy0of07Uhw"
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token is missing", Toast.LENGTH_LONG).show()
            return
        }

        //val id = vehicleId
        //val standid = standId
        //val brandid = 1 //viewBinding.editVehicleBrandEt.text.toString().toInt()
        //val gastypeid = 1
        //val model = "Abbarth" //viewBinding.editVehicleBrandEt.text.toString()
        //val year = 2003
        //val mileage = 3230000
        //val price = 33500 //viewBinding.editVehiclePriceEt.text.toString().toDouble()
        //val availability = true
        //val description = "Bebe como um camelo. Para testar um texto grande."
        //val brandname = "Matavelhos"
        //val gastypename = ""
        //val photos = arrayOf("")
        //val consume = 11.3
        //val location = "Faro"

        val id = vehicleId
        val standid = standId
        val brandid = 1
        val gastypeid = 1
        val model = viewBinding.editVehicleModelEt.text.toString()
        val year = viewBinding.editVehicleYearEt.text.toString().toInt()
        val mileage = viewBinding.editVehicleMileageEt.toString().toInt()
        val price = viewBinding.editVehiclePriceEt.toString().toDouble()
        val availability = viewBinding.editVehicleAvailabilityEt.isChecked
        val description = viewBinding.editVehicleDescriptionEt.toString()
        val consume = viewBinding.editVehicleConsumeEt.toString().toDouble()
        val location = viewBinding.editVehicleLocationEt.toString()
        val photos = arrayOf("")

        try {
            val vehicle = InsertEditVehicleDto(
                standid, brandid, gastypeid, model, year, mileage, price, availability, description, "", "", id, photos, consume, location
            )
            processVehicle(token, vehicle)
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
