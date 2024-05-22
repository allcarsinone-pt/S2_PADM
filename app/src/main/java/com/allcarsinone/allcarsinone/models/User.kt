package com.allcarsinone.allcarsinone.models
import com.google.gson.annotations.SerializedName

data class User (@SerializedName("username") val username: String,
                 @SerializedName("name") val name: String,
                 @SerializedName("email") val email: String,
                 @SerializedName("password") val password: String,
                 @SerializedName("address") val address: String,
                 @SerializedName("postalcode") val postalcode: String,
                 @SerializedName("mobilephone") val mobilephone: String,
                 @SerializedName("role_id") val role_id: Int
                )