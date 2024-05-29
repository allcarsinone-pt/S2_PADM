package com.allcarsinone.allcarsinone.dtos

import android.util.Patterns
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import okhttp3.Address

data class EditUserDto (@SerializedName("username") val username: String,
                        @SerializedName("name") val name: String,
                        @SerializedName("email") val email: String,
                        @SerializedName("password") val password: String,
                        @SerializedName("confirmPassword") val confirmPassword: String
)
{

    init {
        if(!validateName(name))
            throw Exception("Name must be at least 5 characters.")
        if(!validateEmailPatterns(email))
            throw Exception("Email is not valid. Ex: example@allcarsinone.pt")
    }

    fun validateName(name: String?): Boolean {
        return if(name != null) {
            name.length > 5
        } else {
            false
        }
    }

    fun validateEmailPatterns(email: String?): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS.matcher(email)

        return if(email != null) {
            pattern.matches()
        } else {
            false
        }
    }



}