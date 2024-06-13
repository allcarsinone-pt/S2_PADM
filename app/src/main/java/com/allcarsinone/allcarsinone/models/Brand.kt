package com.allcarsinone.allcarsinone.models
import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)