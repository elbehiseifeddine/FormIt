package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (
    @SerializedName("_id") val id : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("picture") val picture : String,
    @SerializedName("firstname") val firstname : String,
    @SerializedName("lastname") val lastname : String,
    @SerializedName("birthdate") val birthdate : String,
    @SerializedName("bookmarked") val bookmarked : List<String>,
    @SerializedName("achievements") val achievements : Int,
    @SerializedName("notification") val notification : List<String>,
    @SerializedName("participated") val participated : List<String>,
    @SerializedName("confirmed") val confirmed : Boolean,
    @SerializedName("baned") val baned : Boolean,
    @SerializedName("address") val address : String,
    @SerializedName("phonenumber") val phonenumber : Int,
    )