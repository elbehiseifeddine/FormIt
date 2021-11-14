package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id") val id : String,
    @SerializedName("login") val login : String,
    @SerializedName("password") val password : String,
    )