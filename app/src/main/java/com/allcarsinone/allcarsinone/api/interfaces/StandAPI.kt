package com.allcarsinone.allcarsinone.api.interfaces

import com.allcarsinone.allcarsinone.models.Location
import com.allcarsinone.allcarsinone.models.Stand
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StandAPI {
    @GET("stands/{standid}")
    fun getStand(@Path("standid")standid: Int): Call<Stand>

    @GET("stands/location/{standid}")
    fun getLocation(@Path("standid") standid: Int): Call<Location>
}