package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.Event
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.adapter.HomeEventAdapter
import com.example.formit.ui.view.activitys.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    lateinit var mSharedPref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        /*activity?.runOnUiThread {
            getCoursesNotParticipated()
        }*/

        progBarFragHome.visibility = View.VISIBLE
        if(isAdded()){
            pulltorefresh.setOnRefreshListener {getCoursesNotParticipated()}
        }
        if (mSharedPref.getString(PICTURE, "").toString() == "avatar default.png") {
            profile_pic!!.setImageResource(R.drawable.male_student)
        } else {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path =
                "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F$filename2?alt=media"
            Log.e("*******************************path image ", path)
            Glide.with(requireActivity())
                .load(path)
                .into(profile_pic)
        }
        tv_CourseSeeAll.setOnClickListener {
            Intent(activity, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }
        tv_EventSeeAll.setOnClickListener {
            Intent(activity, EventsActivity::class.java).also {
                startActivity(it)
            }
        }


    }

    private fun getCoursesNotParticipated() {

            apiInterface.getCoursesNotParticipated(mSharedPref.getString(ID, ""))
                .enqueue(object : Callback<MutableList<Course>> {
                    override fun onResponse(
                        call: Call<MutableList<Course>>, response:
                        Response<MutableList<Course>>
                    ) {

                        val courses = response.body()
                        if(isAdded()) {
                            if (courses != null && courses.isNotEmpty()) {

                                Log.e("aaaacourses", courses.toString())
                                val adapter = HomeCouseAdapter(courses, false)
                                rv_courses.adapter = adapter
                                rv_courses.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                pulltorefresh.isRefreshing = false
                            } else {
                                Log.e("Username or Password wrong", "true")
                                rv_courses.visibility = View.GONE
                                tv_NoAvailableCourses.visibility = View.VISIBLE
                            }

                            progBarFragHome.visibility = View.GONE
                        }

                    }

                    override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                        Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                        if(isAdded()) {
                            progBarFragHome.visibility = View.GONE
                            pulltorefresh.isRefreshing = false

                            Snackbar.make(view as View, "Connection Error", Snackbar.LENGTH_LONG)
                                .setAction("Retry") {
                                    getCoursesNotParticipated()
                                }.show()
                        }
                    }
                })
            Log.e("***************id user ", mSharedPref.getString(ID, "").toString())
            apiInterface.getEventsNotParticipated(mSharedPref.getString(ID, "").toString())

                .enqueue(object : Callback<MutableList<Event>> {
                    override fun onResponse(
                        call: Call<MutableList<Event>>, response:
                        Response<MutableList<Event>>
                    ) {

                        val event = response.body()
                        if(isAdded()) {
                            if (event != null && event.isNotEmpty()) {
                                Log.e("courses", event.toString())

                                val adapter = HomeEventAdapter(event, false)
                                rv_events.adapter = adapter
                                rv_events.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )

                            } else {
                                Log.e("Username or Password wrong", "true")
                                rv_events.visibility = View.GONE
                                tv_NoAvailableEvents.visibility = View.VISIBLE
                            }
                            pulltorefresh.isRefreshing = false
                            scroll_view.visibility = View.VISIBLE
                            iv_no_connection.visibility = View.GONE
                            progBarFragHome.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<MutableList<Event>>, t: Throwable) {
                        Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                        if(isAdded()) {
                            pulltorefresh.isRefreshing = false
                            scroll_view.visibility = View.GONE
                            iv_no_connection.visibility = View.VISIBLE
                            progBarFragHome.visibility = View.GONE
                        }
                        }
                }
                )


    }

    override fun onResume() {

        if(isAdded()){
            progBarFragHome.visibility = View.VISIBLE

            getCoursesNotParticipated()
        }

        super.onResume()
    }
}