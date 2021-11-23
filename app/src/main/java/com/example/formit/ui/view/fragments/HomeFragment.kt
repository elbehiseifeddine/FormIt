package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.HomeCouseAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.formit.R
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.view.activitys.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var rootView: View = inflater.inflate(R.layout.fragment_home, container, false)



        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var coursesList = mutableListOf(
            Course(
                "1",
                "Android Course",
                "You will learn how to manipulate and create an android app using kotlin",
                "Seifeddine El Behi",
                150,
                20,
                50,
                "Android studio with all the sdks installed",
                "12/5/2022",
            ),
            Course(
                "2",
                "Ios Course",
                "You will learn how to manipulate and create an Ios app using swift",
                "Ahmed Ben Dahmen",
                150,
                80,
                40,
                "Android studio with all the sdks installed",
                "12/5/2022",
            ),
            Course(
                "3",
                ".Net Core Course",
                "You will learn how to manipulate and create a backend  using c#",
                "Dali ben chikha",
                150,
                50,
                20,
                "Android studio with all the sdks installed",
                "18/5/2022",
            )
        )
        val apiInterface = ApiInterface.create()
        apiInterface.getAllCourses().enqueue(object : Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                val courses = response.body()
                if (courses != null) {
                    Log.e("courses",courses.toString())
                    val adapter = HomeCouseAdapter(courses)
                    rv_courses.adapter = adapter
                    rv_events.adapter = adapter
                    rv_courses.adapter = adapter
                    rv_courses.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rv_events.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    Log.e("Username or Password wrong","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
            }
        })



        tv_CourseSeeAll.setOnClickListener {
            Intent(activity, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }



    }
}


