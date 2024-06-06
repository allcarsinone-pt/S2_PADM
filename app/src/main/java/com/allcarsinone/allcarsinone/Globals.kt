package com.allcarsinone.allcarsinone

import com.allcarsinone.allcarsinone.api.interfaces.UserAPI
import com.allcarsinone.allcarsinone.api.interfaces.VehicleAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Globals {


    lateinit var userAPI: UserAPI
    lateinit var vehicleAPI: VehicleAPI

    private lateinit var retrofit: Retrofit
    fun initAPIs() {
        // For development: change base url, For production: host at cloud our self host backend
        retrofit = Retrofit.Builder().baseUrl("http://192.168.210.72:3003/").addConverterFactory(GsonConverterFactory.create()).build()
        userAPI = retrofit.create(UserAPI::class.java)
        vehicleAPI = retrofit.create(VehicleAPI::class.java)
    }
}