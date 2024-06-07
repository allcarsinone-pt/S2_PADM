package com.allcarsinone.allcarsinone.models

import com.google.gson.annotations.SerializedName

data class Stand (

    @SerializedName("name"        ) var name        : String,
    @SerializedName("location"    ) var location    : String,
    @SerializedName("phone"       ) var phone       : String,
    @SerializedName("mobilephone" ) var mobilephone : String,
    @SerializedName("schedule"    ) var schedule    : String,
    @SerializedName("userid"      ) var userid      : Int,
    @SerializedName("standid"     ) var standid     : Int

)