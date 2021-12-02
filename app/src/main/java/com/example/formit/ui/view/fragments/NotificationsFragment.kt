package com.example.formit.ui.view.fragments

import android.content.Context
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
import com.example.formit.data.model.Notification
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.adapter.NotificationsAdapter
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.fragment_bookmark.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_bookmark, container, false)
        return rootView
    }
    lateinit var mSharedPref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val apiInterface = ApiInterface.create()
        apiInterface.getUserNotifications(mSharedPref.getString(ID,"")).enqueue(object :
            Callback<MutableList<Notification>> {
            override fun onResponse(
                call: Call<MutableList<Notification>>, response:
                Response<MutableList<Notification>>
            ) {
                val notifications = response.body()
                if (notifications != null) {
                    Log.e("********notifications********", notifications.toString())
                    val adapter = NotificationsAdapter(notifications)
                    rv_courses.adapter = adapter
                    rv_courses.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                } else {
                    Log.e("********notifications******** wrong", "true")
                }
            }

            override fun onFailure(call: Call<MutableList<Notification>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })
    }
}