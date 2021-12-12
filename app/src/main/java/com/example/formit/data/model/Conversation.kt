package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Conversation (
    @SerializedName("_id") val id : String,
    @SerializedName("course") val course : String,
    @SerializedName("members") val members : List<User>,
    @SerializedName("message") val message : List<Message>,

    )