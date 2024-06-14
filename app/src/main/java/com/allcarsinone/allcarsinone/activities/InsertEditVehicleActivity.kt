package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.BrandsSpinnerAdapter
import com.allcarsinone.allcarsinone.adapters.GasTypesSpinnerAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityEditVehicleBinding
import com.allcarsinone.allcarsinone.dtos.InsertEditVehicleDto
import com.allcarsinone.allcarsinone.models.Brand
import com.allcarsinone.allcarsinone.models.GasType
import com.allcarsinone.allcarsinone.models.Vehicle
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests
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
    private lateinit var brand: Brand

    private lateinit var gasType: GasType
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
        val brandsOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewBinding.editVehicleBrandSPIN.setSelection(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                brand = parent?.getItemAtPosition(position) as Brand
            }
        }
        val gasTypesOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewBinding.editVehicleBrandSPIN.setSelection(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gasType = parent?.getItemAtPosition(position) as GasType
            }
        }
        viewBinding.editVehicleBrandSPIN.onItemSelectedListener = brandsOnItemSelectedListener
        viewBinding.editVehicleGastypeSPIN.onItemSelectedListener = gasTypesOnItemSelectedListener

        DatabaseRequests.loadBrands(::brandsCallback)
        DatabaseRequests.loadGasTypes( ::gasTypesCallback)
        if(vehicleID > 0)
            DatabaseRequests.loadVehicle(vehicleID, ::vehiclesCallback )
    }
    private fun fillVehicleForm(v: Vehicle) {
        viewBinding.editVehicleModelEt.setText(v.model)
        viewBinding.editVehicleYearEt.setText(v.year.toString())
        viewBinding.editVehicleMileageEt.setText(v.mileage.toString())
        viewBinding.editVehiclePriceEt.setText(v.price.toString())
        viewBinding.editVehicleAvailabilityEt.setChecked(v.availability)
        viewBinding.editVehicleDescriptionEt.setText(v.description)
        viewBinding.editVehicleConsumeEt.setText(v.consume.toString())
        viewBinding.editVehicleLocationEt.setText(v.location)
    }
    private fun fillBrandSelect(b: List<Brand>) {
        val brandNames = b.map { it.name }
        val adapter = BrandsSpinnerAdapter(this, b.toMutableList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.editVehicleBrandSPIN.adapter = adapter
        viewBinding.editVehicleBrandSPIN.setSelection(0)
    }
    private fun fillGastypeSelect(b: List<GasType>) {
        // TODO: Selecionar a ativa
        val gastypeNames = b.map { it.name }
        val adapter = GasTypesSpinnerAdapter(this, b.toMutableList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.editVehicleGastypeSPIN.adapter = adapter
        viewBinding.editVehicleGastypeSPIN.setSelection(0)
    }
    private fun brandsCallback(b: List<Brand>?, errCode: Int) {
        if(b != null)
            fillBrandSelect(b)
    }
    private fun gasTypesCallback(g: List<GasType>?, errCode: Int) {
        if(g != null)
            fillGastypeSelect(g)
    }
    private fun vehiclesCallback(v: Vehicle?, errCode: Int) {
        if(v != null)
            fillVehicleForm(v)
    }
    private fun processVehiclesCallback(errCode: Int) {
        if(errCode == 200) {
            Toast.makeText(this, "Register successfully processed", Toast.LENGTH_LONG).show()
            val intent = Intent(this, InitialPageStandActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
            Toast.makeText(this, "Error processing", Toast.LENGTH_LONG).show()
    }

    private fun processVehicle(token: String, body: InsertEditVehicleDto) {
        val jsonString = Gson().toJson(body)
        val obj = JsonParser.parseString(jsonString).asJsonObject.toString()
        var rb = RequestBody.create(MediaType.parse("application/json; charset=uft-8"), obj)
        if (body.id.toInt() > 0) {
            DatabaseRequests.editVehicle(body, ::processVehiclesCallback)
        } else {
            DatabaseRequests.insertVehicle("Bearer $token", rb, ::processVehiclesCallback)
        }
    }

    private fun validateDataFields(vehicleId: Int, standId: Int) {
        val sharedPrefs = DataUtils.getSharedPreferences(this@InsertEditVehicleActivity)
        val token = sharedPrefs.getString("token", "")
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NSwidXNlcm5hbWUiOiJ6ZWNhZ2FsaGFvIiwibmFtZSI6IlN0YW5kIFplY2EgR2FsaGFvIiwiZW1haWwiOiJwYXVsb2Fzc2lzdGFudEBnbWFpbC5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRHR3IzdkNJZkdTMUlVZlhtdDlDMG1lRXhCYndXYnBramNjM3VhMGJ1YXlGaGhaeU8yNHA1NiIsImFkZHJlc3MiOiIiLCJjaXR5IjpudWxsLCJwb3N0YWxjb2RlIjoiIiwibW9iaWxlcGhvbmUiOiIiLCJwaG90byI6bnVsbCwicm9sZV9pZCI6MiwiaWF0IjoxNzE4MjAyNzAxLCJleHAiOjE3MTgyMDk5MDF9.bMcZHpaIBBp45ov-jAvF4Yc09gY1qt8GPXy0of07Uhw"
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token is missing", Toast.LENGTH_LONG).show()
            return
        }

        val id = vehicleId
        val standid = standId
        val model = viewBinding.editVehicleModelEt.text.toString()
        val year = viewBinding.editVehicleYearEt.text.toString().toInt()
        val mileage = viewBinding.editVehicleMileageEt.text.toString().toDouble()
        val price = viewBinding.editVehiclePriceEt.text.toString().toDouble()
        val availability = viewBinding.editVehicleAvailabilityEt.isChecked
        val description = viewBinding.editVehicleDescriptionEt.text.toString()
        val consume = viewBinding.editVehicleConsumeEt.text.toString().toDouble()
        val location = viewBinding.editVehicleLocationEt.text.toString()
        val photos = arrayOf("")

        try {
            val brandid = brand.id
            val gastypeid = gasType.id
            val vehicle = InsertEditVehicleDto(
                standid, brandid, gastypeid, model, year, mileage, price, availability, description, "", "", id, photos, consume, location
            )
            processVehicle(token, vehicle)
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
