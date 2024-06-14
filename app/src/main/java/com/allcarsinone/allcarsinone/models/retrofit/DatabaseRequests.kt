package com.allcarsinone.allcarsinone.models.retrofit

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.models.Brand
import com.allcarsinone.allcarsinone.models.GasType
import com.allcarsinone.allcarsinone.models.User
import com.allcarsinone.allcarsinone.models.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatabaseRequests : AppCompatActivity() {
    companion object : AppCompatActivity() {
        private val vehicleAPI by lazy { Globals.vehicleAPI }
        private val gastypeAPI by lazy { Globals.gastypeAPI }
        private val brandAPI by lazy { Globals.brandAPI }
        private val usersAPI by lazy { Globals.userAPI }
        public fun loadVehicle(vehicleID: Int, callback: (Vehicle?, Int) -> Unit) {
            val call: Call<Vehicle> = vehicleAPI.getVehicle(vehicleID)
            call.enqueue(object : Callback<Vehicle> {
                override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                    if (response.isSuccessful) {
                        response.body()?.let { vehicle ->
                            callback(vehicle, response.code())
                        }
                    } else {
                        var v: Vehicle? = null
                        callback(v, response.code())
                        Toast.makeText(this@Companion, response.errorBody()?.string() ?: "Unknown error", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                    Toast.makeText(this@Companion, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
            })
        }
        public fun loadAllVehicles( arg: Any, callback: (List<Vehicle>?, Int, Any) -> Unit) {
            val call: Call<List<Vehicle>> = vehicleAPI.getAll()
            call.enqueue(object : Callback<List<Vehicle>> {
                override fun onResponse(call: Call<List<Vehicle>>, response: Response<List<Vehicle>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { vehicles ->
                            callback(vehicles, response.code(), arg)
                        }
                    } else {
                        var v: List<Vehicle>? = null
                        callback(v, response.code(), arg)
                        Toast.makeText(this@Companion, response.errorBody()?.string() ?: "Unknown error", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                    Toast.makeText(this@Companion, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
            })
        }
        fun loadGasTypes(callback: (List<GasType>?, Int) -> Unit) {
            val call: Call<List<GasType>> = gastypeAPI.getAll()
            call.enqueue(object : Callback<List<GasType>> {
                override fun onResponse(call: Call<List<GasType>>, response: Response<List<GasType>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { gasTypes ->
                            callback(gasTypes, response.code())
                        }
                    } else {
                        var g: List<GasType>? = null
                        callback(g, response.code())
                        Toast.makeText(this@Companion, response.errorBody()?.string() ?: "Unknown error", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<List<GasType>>, t: Throwable) {
                    Toast.makeText(this@Companion, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
            })
        }
        fun loadBrands(callback: (List<Brand>?, Int) -> Unit) {
            val call: Call<List<Brand>> = brandAPI.getAll()
            call.enqueue(object : Callback<List<Brand>> {
                override fun onResponse(call: Call<List<Brand>>, response: Response<List<Brand>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { brands ->
                            callback(brands, response.code())
                        }
                    } else {
                        var b: List<Brand>? = null
                        callback(b, response.code())
                        Toast.makeText(this@Companion, response.errorBody()?.string() ?: "Unknown error", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<List<Brand>>, t: Throwable) {
                    Toast.makeText(this@Companion, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
            })
        }
        fun getLoggedUser( arg: Any, token: String?, callback: (User?, Int, Any) -> Unit) {
            val authHeader = "Bearer $token"
            val loggedUser = usersAPI.validate(authHeader)
            loggedUser.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body()?.let { userGot ->
                            val user = userGot
                            callback(user, response.code(), arg)
                        }
                    } else {
                        var u: User? = null
                        callback(u, response.code(), arg)
                        Toast.makeText(this@Companion, response.errorBody()?.string() ?: "Unknown error", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<User>, response: Throwable) {
                    Toast.makeText(this@Companion, response.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}