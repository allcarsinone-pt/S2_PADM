
package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.ListviewVehiclesAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageBinding
import com.allcarsinone.allcarsinone.dtos.EditVehicleDto
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class InitialPageActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityInitialPageBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInitialPageBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(R.layout.activity_initial_page)

        val spinnerMon = findViewById<Spinner>(R.id.InitPageLocationSpinner_SP)
        val itemsMon = arrayOf("Aveiro", "Bragança", "Braga", "Coimbra", "Faro", "Funchal", "Guarda", "Lisboa", "Portalegre", "Porto", "Santarém", "Viana do Castelo")
        val adapterMon = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMon )
        spinnerMon.setAdapter(adapterMon)

        runVehiclesList()
    }

    private val editProfileContract by lazy {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Toast.makeText(this@InitialPageActivity,
                    "User updated with success!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun runVehiclesList() {
        val call: Call<List<Vehicle>> = vehicleAPI.getAll()

        call.enqueue(object : Callback<List<Vehicle>> {
            override fun onResponse(call: Call<List<Vehicle>>, response: Response<List<Vehicle>>) {
                if (response.isSuccessful) {
                    response.body()?.let { vehicleList ->
                        val list = ArrayList(vehicleList)
                        setupRecyclerView(list)
                    }
                } else {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@InitialPageActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@InitialPageActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                Toast.makeText(this@InitialPageActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecyclerView(vehicleList: ArrayList<Vehicle>) {
        val adapter = ListviewVehiclesAdapter(vehicleList)
        val recyclerView = findViewById<RecyclerView>(R.id.VehiclesListViewView_RecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}