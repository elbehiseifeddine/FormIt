package com.example.formit.ui.view.activitys
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        val apiInterface = ApiInterface.create()
        apiInterface.getAllCourses().enqueue(object : Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                val courses = response.body()
                if (courses != null) {
                    Log.e("coursesaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",courses.toString())
                    val adapter= CoursesAdapter(courses,false)
                    rv_Cour.adapter=adapter
                    rv_Cour.layoutManager =
                        LinearLayoutManager(this@CoursesActivity, LinearLayoutManager.VERTICAL, false)

                } else {
                    Log.e("Username or Password wrong","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
            }
        })

        toolbar_title.text="Courses"
        button_Right.visibility=View.INVISIBLE

        btn_reus_back.setOnClickListener {
            finish()
        }

    }

}