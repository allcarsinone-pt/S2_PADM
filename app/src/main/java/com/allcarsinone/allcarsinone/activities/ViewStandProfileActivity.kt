package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityViewStandProfileBinding
import com.allcarsinone.allcarsinone.models.Coordinates
import com.allcarsinone.allcarsinone.models.Stand
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewStandProfileActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private val viewBinding: ActivityViewStandProfileBinding by lazy { ActivityViewStandProfileBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val standId = intent.getIntExtra("standid", 0) as Int
        getStandDetails(standId)

        viewBinding.viewStandProfileBackbuttonBtn.setOnClickListener {
            finish()
        }

    }

    fun getStandDetails(standid: Int) {
        val standsAPI = Globals.standAPI
        val call = standsAPI.getStand(standid)

        call.enqueue(object : Callback<Stand> {
            override fun onResponse(p0: Call<Stand>, p1: Response<Stand>) {
                when(p1.code()) {
                    200 -> {

                        val stand = p1.body()!!
                        viewBinding.viewStandProfileStandEt.setText(stand.name)
                        viewBinding.viewStandProfileAddressEt.setText(stand.location)
                        viewBinding.viewStandProfileMobilephoneEt.setText(stand.mobilephone)
                        val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.mapFragmentViewStandProfile) as SupportMapFragment
                        mapFragment.getMapAsync(this@ViewStandProfileActivity)
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@ViewStandProfileActivity)


                    }
                }
            }

            override fun onFailure(p0: Call<Stand>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        val standAPI = Globals.standAPI

        val call = standAPI.getLocation(5)

        call.enqueue(object: Callback<Coordinates>{
            override fun onResponse(p0: Call<Coordinates>, p1: Response<Coordinates>) {
                when(p1.code()) {
                    200 -> {
                        mMap.addMarker(MarkerOptions().position(LatLng(p1.body()!!.latitude, p1.body()!!.longitude)))
                        setUpMap(p1.body()!!)
                    }
                }
            }

            override fun onFailure(p0: Call<Coordinates>, p1: Throwable) {
                TODO("Not yet implemented")
            }
        })



    }

    private fun setUpMap(coordinates: Coordinates) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(coordinates.latitude, coordinates.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val intent = Intent(this, ViewStandLocationActivity::class.java)
        intent.putExtra("latitude", p0.position.latitude)
        intent.putExtra("longitude", p0.position.longitude)
        startActivity(intent)
        return true
    }
}