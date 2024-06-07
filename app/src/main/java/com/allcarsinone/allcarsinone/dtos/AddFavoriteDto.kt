package com.allcarsinone.allcarsinone.dtos

import com.google.gson.annotations.SerializedName

class AddFavoriteDto(@SerializedName("vehicleid") val vehicleid:Int, @SerializedName("userid") val userid:Int) {
}