package com.example.formit.data.model

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
    val profilePic: String,
    val achievements: String,
): Serializable