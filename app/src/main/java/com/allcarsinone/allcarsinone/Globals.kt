package com.allcarsinone.allcarsinone

import com.allcarsinone.allcarsinone.api.UserAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Globals {


    lateinit var userAPI: UserAPI
    private lateinit var retrofit: Retrofit
    fun initAPIs() {
        // For development: change base url, For production: host at cloud our self host backend
        retrofit = Retrofit.Builder().baseUrl("https://e0a1-193-137-231-142.ngrok-free.app/").addConverterFactory(GsonConverterFactory.create()).build()
        userAPI = retrofit.create(UserAPI::class.java)


    }
}