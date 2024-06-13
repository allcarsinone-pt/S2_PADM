package com.allcarsinone.allcarsinone.api.interfaces
import com.allcarsinone.allcarsinone.models.GasType
import retrofit2.Call
import retrofit2.http.GET

interface GasTypeAPI {
    @GET("proxy-vehicles/gastypes")
    fun getAll(): Call<List<GasType>>
}