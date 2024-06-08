package com.allcarsinone.allcarsinone.api.interfaces

import com.allcarsinone.allcarsinone.models.Coordinates
import com.allcarsinone.allcarsinone.models.Stand
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StandAPI {
    @GET("proxy-stands/stands/{standid}")
    fun getStand(@Path("standid")standid: Int): Call<Stand>

    @GET("proxy-stands/stands/location/{standid}")
    fun getLocation(@Path("standid") standid: Int): Call<Coordinates>
}