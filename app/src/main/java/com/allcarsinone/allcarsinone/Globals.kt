package com.allcarsinone.allcarsinone

import com.allcarsinone.allcarsinone.api.interfaces.UserAPI
import com.allcarsinone.allcarsinone.api.interfaces.VehiclesAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Globals {


    lateinit var userAPI: UserAPI
    lateinit var vehiclesAPI: VehiclesAPI

    private lateinit var retrofit: Retrofit
    fun initAPIs() {
        // For development: change base url, For production: host at cloud our self host backend
        retrofit = Retrofit.Builder().baseUrl("http://pauloestevao.com:8080/").addConverterFactory(GsonConverterFactory.create()).build()
        userAPI = retrofit.create(UserAPI::class.java)
        vehiclesAPI = retrofit.create(VehiclesAPI::class.java)


    }
}