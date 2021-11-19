package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.HomeCouseAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.formit.R
import com.example.formit.ui.view.activitys.CoursesActivity
import com.example.formit.ui.view.activitys.FIRST_VISIT
import com.example.formit.ui.view.activitys.PREF_NAME


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mSharedPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var rootView: View = inflater.inflate(R.layout.fragment_home, container, false)

        mSharedPref = rootView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if(mSharedPref.getBoolean(FIRST_VISIT,true)){

        }else{
            WelcomeText.text="Welcome Back"
        }

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
                50,
                "Android studio with all the sdks installed",
                "12/5/2022",
                "11/10/2022"
            ),
            Course(
                "2",
                "Ios Course",
                "You will learn how to manipulate and create an Ios app using swift",
                "Ahmed Ben Dahmen",
                150,
                50,
                "Android studio with all the sdks installed",
                "12/5/2022",
                "11/10/2022"
            ),
            Course(
                "3",
                ".Net Core Course",
                "You will learn how to manipulate and create a backend  using c#",
                "Dali ben chikha",
                150,
                50,
                "Android studio with all the sdks installed",
                "18/5/2022",
                "12/10/2022",
            )
        )

        val adapter = HomeCouseAdapter(coursesList)

        tv_CourseSeeAll.setOnClickListener {
            Intent(activity, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }

        rv_events.adapter = adapter
        rv_courses.adapter = adapter
        rv_courses.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_events.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



    }
}


