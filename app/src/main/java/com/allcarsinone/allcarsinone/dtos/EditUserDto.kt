package com.allcarsinone.allcarsinone.dtos

import android.util.Patterns
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import okhttp3.Address

data class EditUserDto (@SerializedName("username") val username: String,
                        @SerializedName("name") val name: String,
                        @SerializedName("email") val email: String,
                        @SerializedName("mobilephone") val mobilephone: String?,
                        @SerializedName("address") val address: String?
)
{

    init {
        if(!validateName(name))
            throw Exception("Name must be at least 5 characters.")
        if(!validateEmailPatterns(email))
            throw Exception("Email is not valid. Ex: example@allcarsinone.pt")

        mobilephone?.let {
            if(!validateMobilephone(mobilephone)) {
                throw Exception("Mobile phone must match phone patterns.")
            }
        }

        address?.let {
            if(!validateAddress(address)) {
                throw Exception("Address must be at least 3 characters.")
            }
        }
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

    fun validateAddress(address: String?): Boolean {
        return if(address != null || address != "") {
            true
        } else {
            false
        }
    }

    fun validateMobilephone(mobilephone: String?): Boolean {
        val pattern = Patterns.PHONE.matcher(mobilephone)

        return if(mobilephone != null) {
            pattern.matches()
        } else {
            false
        }

    }



}