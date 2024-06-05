package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

data class Vehicle (@SerializedName("standid") val standid: Number,
                    @SerializedName("model") val model: String,
                    @SerializedName("year") val year: Number,
                    @SerializedName("mileage") val mileage: Float,
                    @SerializedName("price") val price: Float,
                    @SerializedName("description") val description: String,
                    @SerializedName("gastypename") val gastypename: String,
                    @SerializedName("brandname") val brandname: String
    )