package com.allcarsinone.allcarsinone

import com.allcarsinone.allcarsinone.api.interfaces.BrandAPI
import com.allcarsinone.allcarsinone.api.interfaces.GasTypeAPI
import com.allcarsinone.allcarsinone.api.interfaces.StandAPI
import com.allcarsinone.allcarsinone.api.interfaces.UserAPI
import com.allcarsinone.allcarsinone.api.interfaces.VehicleAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Globals {


    lateinit var userAPI: UserAPI
    lateinit var vehicleAPI: VehicleAPI
    lateinit var standAPI: StandAPI
    lateinit var brandAPI: BrandAPI
    lateinit var gastypeAPI: GasTypeAPI
    private lateinit var retrofit: Retrofit
    fun initAPIs() {
        // For development: change base url, For production: host at cloud our self host backend
        retrofit = Retrofit.Builder().baseUrl("http://5.180.182.3:8080/").addConverterFactory(GsonConverterFactory.create()).build()
        userAPI = retrofit.create(UserAPI::class.java)
        vehicleAPI = retrofit.create(VehicleAPI::class.java)
        standAPI = retrofit.create(StandAPI::class.java)
        brandAPI = retrofit.create(BrandAPI::class.java)
        gastypeAPI = retrofit.create(GasTypeAPI::class.java)
    }
}