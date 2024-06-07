package com.allcarsinone.allcarsinone.api.interfaces
import com.allcarsinone.allcarsinone.dtos.AddFavoriteDto
import com.allcarsinone.allcarsinone.dtos.EditVehicleDto
import com.allcarsinone.allcarsinone.dtos.RegisterVehicleDto
import com.allcarsinone.allcarsinone.models.FavoriteUserCar
import com.allcarsinone.allcarsinone.models.Vehicle
import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path

interface VehicleAPI {
    @Multipart
    @POST("proxy-vehicles/vehicles/")
    fun register(@Header("Authorization") authHeader: String,
                 @Part("vehicle") body: RequestBody,
                 @Part attachments: List<MultipartBody.Part>
    ) : Call<Vehicle>

    @GET("proxy-vehicles/vehicles")
    fun filter(): Call<List<Vehicle>>

    //@GET("proxy-vehicles")
    @GET("proxy-vehicles/vehicles/")
    fun getAll(): Call<List<Vehicle>>

    @GET("proxy-vehicles/vehicles/{vehicleid}")
    fun getVehicle(@Path("vehicleid") vehicleid: Int): Call<Vehicle>

    @PUT("proxy-vehicles/vehicles/{vehicleid}")
    fun edit(@Path("vehicleid") vehicleid: Int, @Body editVehicleDto: EditVehicleDto): Call<Vehicle>

    @POST("proxy-vehicles/vehicles/user/favorites")
    fun addFavorite(@Body favoriteDto: AddFavoriteDto): Call<Unit>

    @GET ("proxy-vehicles/vehicles/user/favorites/{userid}")
    fun getUserFavorites(@Path("userid") userid:Int):Call<List<FavoriteUserCar>>

}