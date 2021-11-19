package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class Notification (
    @SerializedName("_id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("time") val time : String,
)