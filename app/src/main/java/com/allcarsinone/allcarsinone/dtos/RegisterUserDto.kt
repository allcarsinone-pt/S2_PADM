package com.allcarsinone.allcarsinone.dtos

import android.util.Patterns
import android.widget.Toast
import com.google.gson.annotations.SerializedName

data class RegisterUserDto(@SerializedName("username") val username: String,
                           @SerializedName("name") val name: String,
                           @SerializedName("email") val email: String,
                           @SerializedName("password") val password: String,
                           @SerializedName("confirmPassword") val confirmPassword: String,
                           @SerializedName("address") val address: String,
                           @SerializedName("postalcode") val postalcode: String,
                           @SerializedName("mobilephone") val mobilephone: String,
                           @SerializedName("role_id") val role_id: Int
) {
    init {

        if(!validateUsername(username))
            throw Exception("Username must be at least 5 characters.")
        if(!validateName(name))
            throw Exception("Name must be at least 5 characters.")
        if(!validatePassword(password))
            throw Exception("Password must be at least 8 characters.")
        if(!validateConfirmPassword(password, confirmPassword))
            throw Exception("Password and Confirmation Password doesn't match.")
        if(!validateEmailPatterns(email))
            throw Exception("Email is not valid. Ex: example@allcarsinone.pt")

    }


    fun validateUsername(username: String?): Boolean {
        return if(username != null) {
            username.length > 5
        } else {
            false
        }
    }

    fun validateName(name: String?): Boolean {
        return if(name != null) {
            name.length > 5
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

    fun validateConfirmPassword(password: String?, confirmPassword: String?): Boolean {
        return password == confirmPassword
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


