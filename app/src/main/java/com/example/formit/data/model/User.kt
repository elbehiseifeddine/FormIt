package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id") val id : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("picture") val picture : String,
    @SerializedName("firstname") val firstname : String,
    @SerializedName("lastname") val lastname : String,
    @SerializedName("bookmarked") val bookmarked : List<Course>,
    @SerializedName("achievements") val achievements : Int,
    @SerializedName("notification") val notification : List<Notification>,
    @SerializedName("participated") val participated : List<Course>,
    @SerializedName("confirmed") val confirmed : Boolean,
    @SerializedName("baned") val baned : Boolean,
    )