package com.example.formit.data.model

import androidx.annotation.DrawableRes

data class Course_Discussion(

    @DrawableRes
    val CourseDiscussionPic : Int,
    val CourseDiscussionName : String,
    val CourseDiscussionLastMessage : String,
    val CourseDiscussionTime : String,
    val CourseDiscussionUnreaded : Int,
    @DrawableRes
    val CourseDiscussionFirstPic : Int,
    @DrawableRes
    val CourseDiscussionSecondPic : Int,
    @DrawableRes
    val CourseDiscussionThirdPic : Int
)
