package com.example.formit.data.model

import androidx.annotation.DrawableRes

data class Coache_Discussion(
    @DrawableRes
    val CoacheDiscussionPic : Int,
    val CoacheDiscussionName : String,
    val CoacheDiscussionLastMessage : String,
    val CoacheDiscussionTime : String,
    val CoacheDiscussionUnreaded : Int,

)
