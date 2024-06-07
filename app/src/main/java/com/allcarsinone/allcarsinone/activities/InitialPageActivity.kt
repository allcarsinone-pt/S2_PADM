package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.ListviewVehiclesAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageBinding
import com.allcarsinone.allcarsinone.models.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

interface OnVehiclesFetchedListener {
    fun onVehiclesFetched(vehicleList: ArrayList<Vehicle>)
}

class InitialPageActivity : AppCompatActivity(), ListviewVehiclesAdapter.OnItemClickListener, OnVehiclesFetchedListener {
    private lateinit var viewBinding: ActivityInitialPageBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInitialPageBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val spinnerMon = findViewById<Spinner>(R.id.InitPageLocationSpinner_SP)
        val itemsMon = arrayOf("Aveiro", "Bragança", "Braga", "Coimbra", "Faro", "Funchal", "Guarda", "Lisboa", "Portalegre", "Porto", "Santarém", "Viana do Castelo")
        val adapterMon = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemsMon)
        spinnerMon.adapter = adapterMon

        // Initialize RecyclerView
        val recyclerView = viewBinding.VehiclesListViewViewRecycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        runVehiclesList(this)
    }

    override fun onVehiclesFetched(vehicleList: ArrayList<Vehicle>) {
        val recyclerView = viewBinding.VehiclesListViewViewRecycleView
        val adapter = ListviewVehiclesAdapter(vehicleList, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(vehicle: Vehicle) {
        val intent = Intent(this, ViewVehicleActivity::class.java)
        intent.putExtra("vehicleid", vehicle.id.toInt())
        startActivity(intent)
    }

    private fun runVehiclesList(listener: OnVehiclesFetchedListener) {
        val call: Call<List<Vehicle>> = vehicleAPI.getAll()

        call.enqueue(object : Callback<List<Vehicle>> {
            override fun onResponse(call: Call<List<Vehicle>>, response: Response<List<Vehicle>>) {
                if (response.isSuccessful) {
                    response.body()?.let { vehicleList ->
                        val list = ArrayList(vehicleList)
                        listener.onVehiclesFetched(list)
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
}
