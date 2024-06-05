package com.allcarsinone.allcarsinone.api.interfaces

import com.allcarsinone.allcarsinone.dtos.EditUserDto
import com.allcarsinone.allcarsinone.dtos.LoginUserDto
import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.models.Token
import com.allcarsinone.allcarsinone.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.PATCH
interface UserAPI {

    @POST("proxy-auth/users")
    fun register(@Body registerUserDto: RegisterUserDto) : Call<User>

    @POST("proxy-auth/auth")
    fun login(@Body loginUserDto: LoginUserDto): Call<Token>

    @GET("proxy-auth/users/:username")
    fun getUser(@Path("username") username: String): Call<User>

    @PUT("proxy-auth/users/:username")
    fun edit(@Path("username") username: String, @Body editUserDto: EditUserDto): Call<User>

    @GET("proxy-auth/auth")
    fun validate(@Header("Authorization") authHeader: Header): Call<User>
}