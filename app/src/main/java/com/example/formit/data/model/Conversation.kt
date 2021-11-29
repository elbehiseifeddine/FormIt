package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class Conversation (
    @SerializedName("_id") val id : String,
    @SerializedName("course") val course : Course,
    @SerializedName("members") val members : List<String>,
    @SerializedName("message") val message : List<Message>,

    )