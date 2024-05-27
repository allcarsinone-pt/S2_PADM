package com.allcarsinone.allcarsinone.dtos

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class LoginUserDto (@SerializedName("email") val email: String,
                         @SerializedName("password") val password: String)
{

    init {
        if(!validateEmailPatterns(email))
            throw Exception("Email is not valid. Ex: example@allcarsinone.pt")

        if(!validatePassword(password))
            throw Exception("Password must be at least 8 characters.")
    }

    fun validateEmailPatterns(email: String?): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS.matcher(email)

        return if(email != null) {
            pattern.matches()
        } else {
            false
        }
    }

    fun validatePassword(password: String?): Boolean {
        return if(password != null) {
            password.length >= 8
        } else {
            false
        }
    }

}