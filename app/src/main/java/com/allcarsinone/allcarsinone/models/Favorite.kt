package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

class Favorite(@SerializedName("vehicle") private val vehicle: Vehicle, @SerializedName("userid") val userid: Int) {
}