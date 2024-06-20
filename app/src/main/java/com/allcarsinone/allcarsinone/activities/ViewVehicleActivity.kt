package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.CommentsActivity
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityRegisterBinding
import com.allcarsinone.allcarsinone.databinding.ActivityViewVehicleBinding
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import com.bumptech.glide.Glide
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewVehicleActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityViewVehicleBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI}
    private var vehicleID : Int = 0
    private var standID : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityViewVehicleBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        standID = intent.getIntExtra("standid", 0) as Int
        vehicleID = intent.getIntExtra("vehicleid", 0) as Int
        getVehicle(vehicleID)
        viewBinding.viewVehicleLikeIcon.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        viewBinding.ViewVehicleBuyBTN.setOnClickListener {
            val sharedPrefs = DataUtils.getSharedPreferences(context = this)
            val token = sharedPrefs.getString("token", "")
            val validationResult = AuthUtils.validateToken(this, token)
            lateinit var intent: Intent
            if(validationResult.success) {
                intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("vehicleid", vehicleID)
            }
            else {
                AuthUtils.logoutUser(this)
                finish()
                intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("buyFlag", true)
            }
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
        viewBinding.ViewVehicleEditBTN.setOnClickListener {
            val intent = Intent(this, InsertEditVehicleActivity::class.java)
            intent.putExtra("vehicleid", vehicleID)
            startActivity(intent)
        }
        viewBinding.viewVehicleCommentsTV.setOnClickListener {
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("vehicleid", vehicleID)
            startActivity(intent)
        }
        viewBinding.initPageLocationTV.setOnClickListener {
            val intent = Intent(this, ViewStandProfileActivity::class.java)
            intent.putExtra("standid", standID)
            startActivity(intent)
        }
        val sharedPrefs = DataUtils.getSharedPreferences(context = this)
        val token = sharedPrefs.getString("token", "")
        val validationResult = AuthUtils.validateToken(this, token)

        if(validationResult.success) {
            if (validationResult.roleid !== 2) {
                viewBinding.ViewVehicleEditBTN.visibility = View.GONE
            }
        }
        else {
            viewBinding.ViewVehicleEditBTN.visibility = View.GONE
        }
    }

    fun loadData(vehicle: Vehicle) {

        //if(vehicle.standid != LoginStandId || LoginRole != 2 )
        //    viewBinding.ViewVehicleEditBTN.visibility = View.GONE

        viewBinding.ViewVehicleKmsTV.setText(vehicle.mileage.toInt().toString() + ' ' + "kms")
        viewBinding.ViewVehiclePriceTV.setText("€ " + String.format("%.2f", vehicle.price))
        viewBinding.ViewVehicleFuelTV.setText(vehicle.gastypename)
        viewBinding.ViewVehicleDescriptionTV.setText(vehicle.description)
        viewBinding.ViewVehicleIconGearTextTV.setText(vehicle.brandname)
        viewBinding.ViewVehicleIconSitTextTV.setText(vehicle.consume.toInt().toString() + " l/100km")
        viewBinding.initPageCarBrandTV.setText(vehicle.brandname + ' ' + vehicle.model)
        viewBinding.initPageLocationTV.setText(vehicle.location)

        val thumbnail = vehicle.photos[0].url.replace("src/static", "")
        Glide.with(viewBinding.root).load("http://5.180.182.3:8080"+thumbnail).into(viewBinding.ViewVehicleroundedImageView)


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