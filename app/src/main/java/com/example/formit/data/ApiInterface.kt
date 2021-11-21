package com.example.formit.data

import com.example.formit.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface ApiInterface {

    @POST("/users/login")
    fun login(@Body map : HashMap<String, String> ): Call<User>

    @GET("/users/")
    fun getAllUsers(): Call<MutableList<User>>

    @POST("/users/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<User>

    companion object {

        var BASE_URL = "http://192.168.1.15:5000"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}