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
import com.allcarsinone.allcarsinone.AllCarsInOneApplication
import com.allcarsinone.allcarsinone.fragments.MenuFragment
import com.allcarsinone.allcarsinone.AuthUtils
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.adapters.ListviewVehiclesAdapter
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageBinding
import com.allcarsinone.allcarsinone.models.Brand
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests
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
import java.util.Objects

interface OnUsersFetchedListener {
    fun onUsersFetched(user: User)
}
interface OnVehiclesFetchedListener {
    fun onVehiclesFetched(vehicleList: ArrayList<Vehicle>)
}

class InitialPageActivity : AppCompatActivity(), ListviewVehiclesAdapter.OnItemClickListener, OnVehiclesFetchedListener, OnUsersFetchedListener {
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

        DatabaseRequests.loadAllVehicles(this, ::getVehiclesCallback)

        getToken()

        val sharedPrefs = DataUtils.getSharedPreferences(context = this)
        val token = sharedPrefs.getString("token", "")
        if(token != "")
            DatabaseRequests.getLoggedUser(this, token, ::getLoggedUserCallback)


        viewBinding.menuInicial.setOnClickListener {
            toggleFragment()
        }

    }

    private fun toggleFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(MenuFragment::class.java.simpleName)

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment.isVisible)
                transaction.hide(fragment).commit()
            else
                transaction.show(fragment).commit()
        } else {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, MenuFragment.newInstance("", ""), MenuFragment::class.java.simpleName)
            transaction.commit()
        }
    }

    private fun getLoggedUserCallback(u: User?, errCode: Int, arg: Any) {
        if(u == null) {
            AuthUtils.logoutUser(this)
        }
        else {
            val listener = arg as? OnUsersFetchedListener
            listener?.onUsersFetched(u)
        }
    }

    private fun getVehiclesCallback(v: List<Vehicle>?, errCode: Int, arg: Any) {
        if(v != null) {
            val list = ArrayList(v)
            val listener = arg as? OnVehiclesFetchedListener
            listener?.onVehiclesFetched(list)
        }
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

    override fun onUsersFetched(user: User) {
        val loginUser = viewBinding.initPageNameTV
        loginUser.text = user.name
        //val loginPhoto = viewBinding.userImage
        //loginPhoto.setImageURI(user.image)
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
}
