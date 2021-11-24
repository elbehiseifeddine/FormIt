package com.example.formit.data.repository
import com.example.formit.data.model.Course
import com.example.formit.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.*
import java.util.*

interface ApiInterface {

    @POST("/users/login")
    fun login(@Body map : HashMap<String, String> ): Call<User>

    @GET("/users/")
    fun getAllUsers(): Call<MutableList<User>>

    @POST("/users/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<User>

    @GET("/courses")
    fun getAllCourses(): Call<MutableList<Course>>

    @GET("/courses/getCoursesBookmarked/{idUser}")
    fun getCoursesBookmarked(@Path("idUser") id: String?): Call<MutableList<Course>>

    @GET("/courses/getCoursesParticipated/{idUser}")
    fun getCoursesParticipated(@Path("idUser") id: String?): Call<MutableList<Course>>

    @GET("/courses/getCoursesNotParticipated/{idUser}")
    fun getCoursesNotParticipated(@Path("idUser") id: String?): Call<MutableList<Course>>

    @PATCH("/users/update/{id}")
    fun UpdateCurrentUser(@Path("id") id: String?, @Body map : HashMap<String, String>): Call<String>

    @PATCH("/users/addBookmark/{id}/{idCourse}")
    fun AddBookmark(@Path("id") id: String?,@Path("idCourse") idCourse: String?): Call<User>

    @PATCH("/users/addParticipation/{id}/{idCourse}")
    fun AddParticipation(@Path("id") id: String?,@Path("idCourse") idCourse: String?): Call<String>

    companion object {


        var BASE_URL = "http://192.168.43.252:5000"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}