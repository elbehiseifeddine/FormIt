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
    lateinit var mSharedPref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val apiInterface = ApiInterface.create()
        apiInterface.getCoursesNotParticipated(mSharedPref.getString(ID, "")).enqueue(object : Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                val courses = response.body()
                if (courses != null) {
                    Log.e("courses",courses.toString())
                    val adapter = HomeCouseAdapter(courses,false)
                    rv_courses.adapter = adapter
                    rv_events.adapter = adapter
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


