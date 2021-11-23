package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mSharedPref: SharedPreferences
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
        var frg: Fragment? = null
        frg = activity?.supportFragmentManager?.findFragmentById(R.id.fl_Fragment)
        val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        if (frg != null) {
            ft.detach(frg)

            ft.attach(frg)
            ft.commit()
        }
        toolbar_title.text="Profile"

        button_Right.setBackgroundResource(R.drawable.ic_logout)

        button_Right.setOnClickListener{

            val builder = AlertDialog.Builder(it.context)
            builder.setTitle(getString(R.string.logoutTitle))
            builder.setMessage(R.string.logoutMessage)
            builder.setPositiveButton("Yes"){ dialogInterface, which ->
                activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                    ?.edit()
                    ?.clear()?.apply()
                Intent(context, SignInUpActivity::class.java).also {
                    startActivity(it)
                    activity?.finish()

            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()
        }

            }
        }
        btn_reus_back.visibility=View.GONE

        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

//        val adapter = HomeCouseAdapter(coursesList)

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

        ProfileSettings.setOnClickListener{
            Intent(activity, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }

        tv_ProfileFullName.setText(mSharedPref.getString(FIRSTNAME,"").toString()+" "+mSharedPref.getString(LASTNAME,"").toString())
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL,"").toString())



//        Profile_Participated_events.adapter = adapter
//        Profile_Enrolled_courses.adapter = adapter
//        Profile_Enrolled_courses.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        Profile_Participated_events.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


}