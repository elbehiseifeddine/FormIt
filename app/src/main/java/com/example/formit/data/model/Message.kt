package com.example.formit.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Message (
    @SerializedName("_id") val id : String,
    @SerializedName("conversationId") val conversationId : String,
    @SerializedName("author") val author : String,
    @SerializedName("message") var message : String,
    @SerializedName("attatchments") val attatchments : String,
    @SerializedName("isSend") val isSend : Boolean,
    @SerializedName("createdAt") val createdAt : Date,
        )