package com.example.formit.data

import com.example.formit.data.model.Student
import com.example.formit.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.HashMap

interface ApiInterface {

    @POST("/login")
    fun executeLogin(@Query("map")map: HashMap<String, String>): Call<Student>

    @POST("/signup")
    fun executeSignUp(@Query("map")map: HashMap<String, String>): Call<Void>

    companion object {

        var BASE_URL = "http://localhost:3000"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}