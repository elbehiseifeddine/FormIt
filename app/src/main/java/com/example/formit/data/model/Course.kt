package com.example.formit.data.model

import java.io.Serializable
import java.util.*

data class Course(
    val courseName: String,
    val description: String,
    val mentor: String,
    val price: Int,
    val duration: Int,
    val prerequisites: String,
    val startDate: String,
) : Serializable