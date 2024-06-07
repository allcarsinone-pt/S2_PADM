package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

data class Vehicle (@SerializedName("standid") val standid: Number,
                    @SerializedName("brandid") val brandid: Number,
                    @SerializedName("gastypeid") val gastypeid: Number,
                    @SerializedName("model") val model: String,
                    @SerializedName("year") val year: Number,
                    @SerializedName("mileage") val mileage: Float,
                    @SerializedName("price") val price: Float,
                    @SerializedName("availability") val availability: Boolean,
                    @SerializedName("description") val description: String,
                    @SerializedName("gastypename") val gastypename: String,
                    @SerializedName("brandname") val brandname: String,
                    @SerializedName("consume") val consume: Float,
                    @SerializedName("photos") val photos: List<String>,
                    @SerializedName("id") val id: Number
    )