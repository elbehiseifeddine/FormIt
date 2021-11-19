package com.example.formit.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.view.activitys.CoursesActivity
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.SignInUpActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_title.text="Profile"

        button_Right.setBackgroundResource(R.drawable.ic_logout)

        button_Right.setOnClickListener{
            activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                ?.edit()
                ?.clear()?.apply()
            Intent(context, SignInUpActivity::class.java).also {
                startActivity(it)
                activity?.finish()
            }
        }
        btn_reus_back.visibility=View.GONE






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

        Profile_CourseSeeAll.setOnClickListener {
            Intent(activity, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }

        Profile_EventsSeeAll.setOnClickListener {
            Intent(activity, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }

        Profile_Participated_events.adapter = adapter
        Profile_Enrolled_courses.adapter = adapter
        Profile_Enrolled_courses.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        Profile_Participated_events.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


}