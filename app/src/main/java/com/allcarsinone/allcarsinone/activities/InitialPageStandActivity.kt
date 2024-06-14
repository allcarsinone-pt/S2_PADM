package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
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
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.SupportMapFragment
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

        viewBinding.menuStand.setOnClickListener {
            toggleFragment()
        }

        // Initialize RecyclerView
        val recyclerView = viewBinding.VehiclesListStandViewRecycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        DatabaseRequests.loadAllVehicles(this, ::getVehiclesCallback)

        getToken()

        val sharedPrefs = DataUtils.getSharedPreferences(context = this)
        val token = sharedPrefs.getString("token", "")
        if(token != "")
            DatabaseRequests.getLoggedUser(this, token, ::getLoggedUserCallback)
        else
            Toast.makeText(this@InitialPageStandActivity, "No token.", Toast.LENGTH_LONG).show()
    }

    private fun getLoggedUserCallback(u: User?, errCode: Int, arg: Any) {
        if(u == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            val listener = arg as? OnUsersStandFetchedListener
            listener?.onUsersStandFetched(u)
        }
    }

    private fun getVehiclesCallback(v: List<Vehicle>?, errCode: Int, arg: Any) {
        if(v != null) {
            val list = ArrayList(v)
            val listener = arg as? OnVehiclesStandFetchedListener
            listener?.onVehiclesStandFetched(list)
        }
    }

    private fun toggleFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(MenuStandFragment::class.java.simpleName)

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment.isVisible) {
                transaction.hide(fragment).commit()
            } else {
                transaction.show(fragment).commit()
            }
        } else {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, MenuStandFragment.newInstance("", ""), MenuStandFragment::class.java.simpleName)
            transaction.commit()
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
}
