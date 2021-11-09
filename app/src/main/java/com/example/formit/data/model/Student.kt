package com.example.formit.data.model

import androidx.annotation.DrawableRes
import java.io.Serializable
import java.util.*

data class Student (
    val id_student: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val sexe: String,
    val birthdate: Date,
    @DrawableRes
    val profilePic: Int,
    val achievements: String,
): Serializable