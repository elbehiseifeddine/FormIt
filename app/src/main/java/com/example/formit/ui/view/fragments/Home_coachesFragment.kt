package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.Event
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesCoachAdapter
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.adapter.HomeEventAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.pulltorefresh
import kotlinx.android.synthetic.main.fragment_home_coaches.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class home_coachesFragment : Fragment(R.layout.fragment_home_coaches) {


    lateinit var mSharedPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_coaches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        pulltorefresh.setOnRefreshListener {getOwnCourses()}
        if (mSharedPref.getString(PICTURE, "").toString() == "avatar default.png") {
            profile_pic!!.setImageResource(R.drawable.male_student)
        } else {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path =
                "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F$filename2?alt=media"
            Log.e("*******************************path image ", path)
            Glide.with(requireActivity())
                .load(path)
                .into(coache_profile_pic)
        }




    }

    private fun getOwnCourses() {

        apiInterface.getOwnCourse(mSharedPref.getString(ID, ""))
            .enqueue(object : Callback<MutableList<Course>> {
                override fun onResponse(
                    call: Call<MutableList<Course>>, response:
                    Response<MutableList<Course>>
                ) {
                    val courses = response.body()
                    if (courses != null) {

                        Log.e("courses", courses.toString())
                        val adapter = CoursesCoachAdapter(courses)
                        rv_coache_courses.adapter = adapter
                        rv_coache_courses.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        pulltorefresh.isRefreshing = false
                    } else {
                        Log.e("Username or ----Password wrong", "true")
                    }
                }

                override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                    Log.e("aaaaa", "true")
                }
            })
        Log.e("***************id user ", mSharedPref.getString(ID, "").toString())



    }

    override fun onResume() {

        getOwnCourses()
        super.onResume()
    }




}