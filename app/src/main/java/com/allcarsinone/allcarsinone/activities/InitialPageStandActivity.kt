package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.ListViewVehiclesStandAdapter
import com.allcarsinone.allcarsinone.adapters.ListviewVehiclesAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageBinding
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageStandBinding
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

interface OnUsersStandFetchedListener {
    fun onUsersStandFetched(user: User)
}
interface OnVehiclesStandFetchedListener {
    fun onVehiclesStandFetched(vehicleList: ArrayList<Vehicle>)
}

class InitialPageStandActivity : AppCompatActivity(), ListViewVehiclesStandAdapter.OnItemStandClickListener, OnVehiclesStandFetchedListener, OnUsersStandFetchedListener {
    private lateinit var viewBinding: ActivityInitialPageStandBinding
    private val vehicleAPI by lazy { Globals.vehicleAPI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInitialPageStandBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        // Initialize RecyclerView
        val recyclerView = viewBinding.VehiclesListStandViewRecycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        runVehiclesList(this)
        getToken()

        getLoggedUser(this)
    }

    private fun getLoggedUser(listener: OnUsersStandFetchedListener) {
        val sharedPrefences = DataUtils.getSharedPreferences(context = this)
        val token = sharedPrefences.getString("token", "")
        if(token == "")
            return
        val usersAPI = Globals.userAPI
        val authHeader = "Bearer $token"
        val loggedUser = usersAPI.validate(authHeader)
        loggedUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let { userGot ->
                        val user = userGot
                        listener.onUsersStandFetched(user)
                    }
                } else {
                    when (response.code()) {
                        401 -> { Toast.makeText(this@InitialPageStandActivity, "No token.", Toast.LENGTH_LONG).show()
                        }
                        500 -> {
                            Toast.makeText(this@InitialPageStandActivity, "Internal server error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<User>, response: Throwable) {
                Toast.makeText(this@InitialPageStandActivity, response.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun sendTokenToServer(token: String?) {
        val deviceToken = hashMapOf(
            "token" to token,
        )
        // Get user ID from Firebase Auth or your own server
        Firebase.firestore.collection("tokens").document("tokenid")
            .set(deviceToken)
    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                Log.w("Main", "Fetching FCM register token failed", task.exception)
                return@addOnCompleteListener
            }

            val sharedPrefences = DataUtils.getSharedPreferences(context = this)
            val tokenStored = sharedPrefences.getString("device_token", "")
            val token = task.result
            lifecycleScope.launch {
                if (tokenStored == "" || tokenStored != token)
                {
                    // Add token and timestamp to Firestore for this user
                    val deviceToken = hashMapOf(
                        "token" to token,
                        "timestamp" to FieldValue.serverTimestamp(),
                    )

                    // Get user ID from Firebase Auth or your own server
                    Firebase.firestore.collection("tokens").document(token)
                        .set(deviceToken).await()
                }
            }

            Log.w("Main", token)
        }
    }

    override fun onUsersStandFetched(user: User) {
        //val loginUser = viewBinding.initPageNameTV
        //loginUser.text = user.name
        //val loginPhoto = viewBinding.userImage
        //loginPhoto.setImageURI(user.image)
    }

    override fun onVehiclesStandFetched(vehicleList: ArrayList<Vehicle>) {
        val recyclerView = viewBinding.VehiclesListStandViewRecycleView
        val adapter = ListViewVehiclesStandAdapter(vehicleList, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(vehicle: Vehicle) {
        val intent = Intent(this, ViewVehicleActivity::class.java)
        intent.putExtra("vehicleid", vehicle.id.toInt())
        startActivity(intent)
    }

    private fun runVehiclesList(listener: OnVehiclesStandFetchedListener) {
        val call: Call<List<Vehicle>> = vehicleAPI.getAll()

        call.enqueue(object : Callback<List<Vehicle>> {
            override fun onResponse(call: Call<List<Vehicle>>, response: Response<List<Vehicle>>) {
                if (response.isSuccessful) {
                    response.body()?.let { vehicleList ->
                        val list = ArrayList(vehicleList)
                        listener.onVehiclesStandFetched(list)
                    }
                } else {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(this@InitialPageStandActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@InitialPageStandActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                Toast.makeText(this@InitialPageStandActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
