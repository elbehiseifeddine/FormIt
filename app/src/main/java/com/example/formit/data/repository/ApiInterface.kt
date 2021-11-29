package com.example.formit.data.repository
import com.example.formit.data.model.Conversation
import com.example.formit.data.model.Course
import com.example.formit.data.model.Message
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

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: String?): Call<User>

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
    fun UpdateCurrentUser(@Path("id") id: String?, @Body map : HashMap<String, String>): Call<User>

    @PATCH("/users/addBookmark/{id}/{idCourse}")
    fun AddBookmark(@Path("id") id: String?,@Path("idCourse") idCourse: String?): Call<User>

    @PATCH("/users/addParticipation/{id}/{idCourse}")
    fun AddParticipation(@Path("id") id: String?,@Path("idCourse") idCourse: String?): Call<String>

    @GET("/conversations/getOwnConversations/{id}")
    fun getOwnConversations(@Path("id") id: String?): Call<MutableList<Conversation>>

    @GET("/messages/getChatMessages/{id}")
    fun getConversationMessages(@Path("id") id: String?): Call<MutableList<Message>>

    @GET("/messages/sendMessage/{idConversation}/{idUser}/{message}")
    fun sendMessages(@Path("idConversation") idConversation: String?,@Path("idUser") idUser: String?,@Path("message") message :String? ): Call<Message>


    @GET("/messages/getMessageById/{id}")
    fun getMessageById(@Path("id") id: String?): Call<Message>

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