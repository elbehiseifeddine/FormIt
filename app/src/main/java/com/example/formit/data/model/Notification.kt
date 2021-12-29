package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Notification (
    @SerializedName("_id") val id : String,
    @SerializedName("notificationTitle") val notificationTitle : String,
    @SerializedName("notificationDescription") val notificationDescription : String,
    @SerializedName("notificationCourses") val notificationCourses : MutableList<String>,
    @SerializedName("notificationEvents") val notificationEvents : MutableList<String>,
    @SerializedName("createdAt") val createdAt : Date,
)