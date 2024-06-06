package com.allcarsinone.allcarsinone.api.interfaces
import com.allcarsinone.allcarsinone.dtos.EditVehicleDto
import com.allcarsinone.allcarsinone.dtos.RegisterVehicleDto
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface VehicleAPI {

    @POST("/vehicles/")
    fun register(@Header("Authorization") authHeader: String, @Body body: String) : Call<Vehicle>

    @GET("proxy-vehicles")
    fun filter(): Call<List<Vehicle>>

    @GET("proxy-vehicles/:vehicleid")
    fun getVehicle(@Path("vehicleid") vehicleid: Number): Call<Vehicle>

    @PUT("proxy-vehicles/:vehicleid")
    fun edit(@Path("vehicleid") vehicleid: Number, @Body editVehicleDto: EditVehicleDto): Call<Vehicle>

}