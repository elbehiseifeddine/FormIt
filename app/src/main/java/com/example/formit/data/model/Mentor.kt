package com.example.formit.data.model

import java.io.Serializable
import java.util.*

data class Mentor(
    val id_mentor: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val sexe: String,
    val birthdate: Date,
    val profilePic: String,
    val rate: Float,
) : Serializable