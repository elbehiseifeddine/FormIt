package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Event(
    @SerializedName("_id") val id : String,
    @SerializedName("eventName") val eventName: String,
    @SerializedName("description") val description: String,
    @SerializedName("places") val places: Int,
    @SerializedName("duration") val duration: Int,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("color") val color: String,
    @SerializedName("image") val image: String,
    @SerializedName("participatedMembers") val participatedMembers: Array<String>,
) : Serializable