package com.example.formit.data.model

import com.google.gson.annotations.SerializedName

data class Message (
    @SerializedName("_id") val id : String,
    @SerializedName("conversationId") val conversationId : String,
    @SerializedName("author_id") val author_id : String,
    @SerializedName("attatchments") val attatchments : String,
    @SerializedName("isSend") val isSend : Boolean,
        )