package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class Conversation (
    @SerializedName("_id") val id : String,
    @SerializedName("course") val course : Course,
    @SerializedName("_id") val members : MutableList<User>,
    @SerializedName("_id") val message : MutableList<Message>,

    )