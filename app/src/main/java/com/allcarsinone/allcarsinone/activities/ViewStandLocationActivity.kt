package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityViewStandLocationBinding
import com.allcarsinone.allcarsinone.models.Coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class ViewStandLocationActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private val viewBinding by lazy { ActivityViewStandLocationBinding.inflate(layoutInflater) }
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var coordinates: Coordinates

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        coordinates = Coordinates(intent.getDoubleExtra("latitude", 0.0 ), intent.getDoubleExtra("longitude", 0.0))
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragmentViewStandLocation) as SupportMapFragment
        mapFragment.getMapAsync(this@ViewStandLocationActivity)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@ViewStandLocationActivity)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.addMarker(MarkerOptions().position(LatLng(coordinates.latitude, coordinates.longitude)))
        setUpMap(coordinates)
    }

    private fun setUpMap(coordinates: Coordinates) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
           LOCATION_PERMISSION_REQUEST_CODE
            )
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
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("https://maps.google.pt/?q=${coordinates.latitude},${coordinates.longitude}"))
        startActivity(intent)
        return true
    }
}