package com.example.formit.ui.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.formit.R
import kotlinx.android.synthetic.main.custom_popup.*


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

        val profile_Fragment = ProfileFragment()
        var bottomnavigation: BottomNavigationView
        var coursesList = mutableListOf(
            Course(
                "Android Course",
                "You will learn how to manipulate and create an android app using kotlin",
                "Seifeddine El Behi",
                150,
                50,
                "Android studio with all the sdks installed",
                "12/5/2022"
            ),
            Course(
                "Ios Course",
                "You will learn how to manipulate and create an Ios app using swift",
                "Ahmed Ben Dahmen",
                150,
                50,
                "Android studio with all the sdks installed",
                "12/5/2022"
            ),
            Course(
                ".Net Core Course",
                "You will learn how to manipulate and create a backend  using c#",
                "Dali ben chikha",
                150,
                50,
                "Android studio with all the sdks installed",
                "12/5/2022"
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

        //Pop up menu for user from his profile picture

       /* profile_pic.setOnClickListener {



            val popupmenu = PopupMenu(context, it)
            popupmenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.CheckProfile -> {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fl_Fragment, profile_Fragment)?.commit()
                        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.selectedItemId =
                            R.id.miprofile

                    }

                    R.id.Logout -> {//TODO 5 "Clear the SharedPreferences file and destroy the activity"
                        activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                            ?.edit()
                            ?.clear()?.apply()
                        Intent(context, SignInUpActivity::class.java).also {
                            startActivity(it)
                            activity?.finish()
                        }
                    }

                    else -> false
                }

                true
            }
            popupmenu.inflate(R.menu.popup_menu)
            popupmenu.show()
        }*/

    }
}


