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
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.view.activitys.CoursesActivity
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.apiInterface
import kotlinx.android.synthetic.main.fragment_bookmark.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_bookmark, container, false)
        return rootView
    }
    lateinit var mSharedPref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        apiInterface.getCoursesBookmarked(mSharedPref.getString(ID,"")).enqueue(object : Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                val courses = response.body()
                if (courses != null && courses.isNotEmpty()) {
                    Log.e("courses", courses.toString())
                    val adapter = CoursesAdapter(courses,true)
                    rv_courses.adapter = adapter
                    rv_courses.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                } else {
                    Log.e("Username or Password wrong", "true")
                }
            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })
    }
}