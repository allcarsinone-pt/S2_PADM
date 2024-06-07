package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import com.allcarsinone.allcarsinone.databinding.ActivityViewVehicleBinding
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewVehicleActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityViewVehicleBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityViewVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        getVehicle(1)
    }

    fun loadData(vehicle: Vehicle) {
        viewBinding.ViewVehicleKmsTV.setText(vehicle.mileage.toInt().toString())
        viewBinding.ViewVehiclePriceTV.setText(vehicle.price.toInt().toString()+ ' ' + '€')
        viewBinding.ViewVehicleFuelTV.setText(vehicle.gastypename)
        viewBinding.ViewVehicleDescriptionTV.setText(vehicle.description)
        viewBinding.ViewVehicleIconGearTextTV.setText(vehicle.brandname)
        viewBinding.initPageCarBrandTV.setText(vehicle.brandname + ' ' + vehicle.model)
    }

    fun getVehicle(vehicleId: Int) {
        val call = vehicleAPI.getVehicle(vehicleId)

        call.enqueue(object: Callback<Vehicle> {
            override fun onResponse(p0: Call<Vehicle>, p1: Response<Vehicle>) {
                when(p1.code()) {
                    201 -> {
                        loadData(p1.body()!!)
                        Toast.makeText(this@ViewVehicleActivity, p1.body()!!.description, Toast.LENGTH_LONG).show()
                    }
                    400 -> {
                        Toast.makeText(this@ViewVehicleActivity, JSONObject(p1.errorBody()?.string()).getString("message"), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(p0: Call<Vehicle>, p1: Throwable) {
                Toast.makeText(this@ViewVehicleActivity, p1.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}