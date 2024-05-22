package com.allcarsinone.allcarsinone.api

import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.PATCH
interface UserAPI {

    @POST("proxy-auth/users")
    fun register(@Body registerUserDto: RegisterUserDto) : Call<User>

}