package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

data class FavoriteUserCar (
    @SerializedName("vehicleid") var vehicleid : Int,
    @SerializedName("carname"   ) var carname: String,
    @SerializedName("price"     ) var price: Int,
    @SerializedName("thumbnail") var thumbnail: String
)