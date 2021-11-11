package com.example.formit.data.model

import androidx.annotation.DrawableRes

data class Buble_Message(

    @DrawableRes
    val profilePic: Int,
    val userName:String,
    val userConnected : Boolean
)
