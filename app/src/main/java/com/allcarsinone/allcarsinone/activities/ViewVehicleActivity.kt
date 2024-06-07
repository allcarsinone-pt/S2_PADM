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
    private var vehicleID : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityViewVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        vehicleID = intent.getIntExtra("vehicleid", 0) as Int
        print("VEHICLE ID ->> " + vehicleID)
        getVehicle(vehicleID)
        viewBinding.ViewVehicleBuyBTN.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("vehicleid", vehicleID)
            startActivity(intent)
        }
        viewBinding.testDriveTv.setOnClickListener {
            val intent = Intent(this, BookTestDriveActivity::class.java)
            intent.putExtra("vehicleid", vehicleID)
            startActivity(intent)
        }
        viewBinding.viewVehicleBackIcon.setOnClickListener {
            finish()
        }
    }

    fun loadData(vehicle: Vehicle) {
        viewBinding.ViewVehicleKmsTV.setText(vehicle.mileage.toInt().toString() + ' ' + "kms")
        viewBinding.ViewVehiclePriceTV.setText(vehicle.price.toInt().toString()+ ' ' + '€')
        viewBinding.ViewVehicleFuelTV.setText(vehicle.gastypename)
        viewBinding.ViewVehicleDescriptionTV.setText(vehicle.description)
        viewBinding.ViewVehicleIconGearTextTV.setText(vehicle.brandname)
        viewBinding.ViewVehicleIconSitTextTV.setText(vehicle.consume.toInt().toString() + " l/100km")
        viewBinding.initPageCarBrandTV.setText(vehicle.brandname + ' ' + vehicle.model)
        vehicleID = vehicle.id.toInt()
    }

    fun getVehicle(vehicleId: Int) {
        val call = vehicleAPI.getVehicle(vehicleId)

        call.enqueue(object: Callback<Vehicle> {
            override fun onResponse(p0: Call<Vehicle>, p1: Response<Vehicle>) {
                when(p1.code()) {
                    201 -> {
                        loadData(p1.body()!!)
                    }
                    400 -> {
                        Toast.makeText(this@ViewVehicleActivity, JSONObject(p1.errorBody()?.string()).getString("error"), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(p0: Call<Vehicle>, p1: Throwable) {
                Toast.makeText(this@ViewVehicleActivity, p1.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}