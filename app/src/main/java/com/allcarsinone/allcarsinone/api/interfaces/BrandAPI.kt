package com.allcarsinone.allcarsinone.api.interfaces

import com.allcarsinone.allcarsinone.dtos.EditUserDto
import com.allcarsinone.allcarsinone.dtos.LoginUserDto
import com.allcarsinone.allcarsinone.dtos.RegisterUserDto
import com.allcarsinone.allcarsinone.models.Brand
import com.allcarsinone.allcarsinone.models.FavoriteUserCar
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
interface BrandAPI {
    @GET("proxy-vehicles/brands")
    fun getAll(): Call<List<Brand>>
}