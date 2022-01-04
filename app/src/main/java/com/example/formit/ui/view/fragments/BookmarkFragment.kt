package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.apiInterface
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_bookmark.iv_no_connection
import kotlinx.android.synthetic.main.fragment_bookmark.pulltorefresh
import kotlinx.android.synthetic.main.fragment_bookmark.rv_courses
import kotlinx.android.synthetic.main.fragment_bookmark.scroll_view
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
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
        toolbar_title.text = "My BookMark"
        button_Right.visibility = View.GONE
        btn_reus_back.visibility = View.INVISIBLE
        progBarFragBookmark.visibility = View.VISIBLE


        pulltorefresh.setOnRefreshListener {

            apiInterface.getCoursesBookmarked(mSharedPref.getString(ID, ""))
                .enqueue(object : Callback<MutableList<Course>> {
                    override fun onResponse(
                        call: Call<MutableList<Course>>, response:
                        Response<MutableList<Course>>
                    ) {


                        val courses = response.body()
                        if (courses != null && courses.isNotEmpty()) {
                            Log.e("courses", courses.toString())
                            val adapter = CoursesAdapter(courses, true)
                            rv_courses.adapter = adapter
                            rv_courses.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        } else {
                            Log.e("Username or Password wrong", "true")
                            rv_courses.visibility=View.GONE
                            tv_NoBookmarksYet.visibility=View.VISIBLE
                        }
                        pulltorefresh.isRefreshing = false
                        scroll_view.visibility=View.VISIBLE
                        iv_no_connection.visibility=View.GONE
                        progBarFragBookmark.visibility = View.GONE


                    }

                    override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                        Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                        pulltorefresh.isRefreshing = false
                        scroll_view.visibility=View.GONE
                        iv_no_connection.visibility=View.VISIBLE
                        progBarFragBookmark.visibility = View.GONE
                    }
                }
                )
        }
    }
    override fun onResume() {
        if(isAdded()){
            progBarFragBookmark.visibility = View.VISIBLE


            apiInterface.getCoursesBookmarked(mSharedPref.getString(ID, ""))
            .enqueue(object : Callback<MutableList<Course>> {
                override fun onResponse(
                    call: Call<MutableList<Course>>, response:
                    Response<MutableList<Course>>
                ) {


                    val courses = response.body()
                    if(isAdded()) {
                        if (courses != null && courses.isNotEmpty()) {
                            Log.e("courses", courses.toString())
                            val adapter = CoursesAdapter(courses, true)
                            rv_courses.adapter = adapter
                            rv_courses.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            pulltorefresh.isRefreshing = false
                        } else {
                            Log.e("Username or Password wrong", "true")
                        }
                        progBarFragBookmark.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                    if(isAdded()) {
                        pulltorefresh.isRefreshing = false
                        scroll_view.visibility = View.GONE
                        iv_no_connection.visibility = View.VISIBLE
                        progBarFragBookmark.visibility = View.GONE
                    }
                    }
            }
            )}
        super.onResume()
    }
}