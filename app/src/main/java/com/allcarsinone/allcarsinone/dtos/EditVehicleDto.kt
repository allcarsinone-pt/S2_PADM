package com.allcarsinone.allcarsinone.dtos

import com.google.gson.annotations.SerializedName
data class EditVehicleDto (@SerializedName("standid") val standid: Number,
                        @SerializedName("brandid") val brandid: Number,
                        @SerializedName("gastypeid") val gastypeid: Number,
                        @SerializedName("model") val model: String?,
                        @SerializedName("year") val year: Number,
                        @SerializedName("mileage") val mileage: Number,
                        @SerializedName("price") val price: Number,
                        @SerializedName("availability") val availability: Boolean,
                        @SerializedName("description") val description: String,
                        @SerializedName("brandname") val brandname: String,
                        @SerializedName("gastypename") val gastypename: String,
                        @SerializedName("id") val id: Number,
                        //@SerializedName("photos") val photos: Array<String>,
                        @SerializedName("consume") val consume: Double,
)