package com.example.formit.data.model

import java.io.Serializable
import java.util.*

data class Course(
    val id_course: Int,
    val courseName: String,
    val description: String,
    val mentor: String,
    val price: Int,
    val duration: String,
    val Prerequisites: String,
    val startDate: Date,
) : Serializable