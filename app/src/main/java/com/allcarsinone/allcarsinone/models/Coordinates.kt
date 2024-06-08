package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

data class Coordinates (

    @SerializedName("latitude"  ) var latitude  : Double,
    @SerializedName("longitude" ) var longitude : Double

)