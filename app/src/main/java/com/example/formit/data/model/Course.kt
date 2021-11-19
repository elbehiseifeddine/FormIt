package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Course(
    @SerializedName("_id") val id : String,
    @SerializedName("_courseName") val courseName: String,
    @SerializedName("_description") val description: String,
    @SerializedName("_mentor") val mentor: String,
    @SerializedName("_price") val price: Int,
    @SerializedName("_duration") val duration: Int,
    @SerializedName("_prerequisites") val prerequisites: String,
    @SerializedName("_startDate") val startDate: String,
    @SerializedName("_creationDate") val creationDate: String,
) : Serializable