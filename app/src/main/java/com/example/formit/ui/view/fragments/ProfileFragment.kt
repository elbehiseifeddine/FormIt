package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var rootView: View = inflater.inflate(R.layout.fragment_profile, container, false)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_title.text = "Profile"

        button_Right.setBackgroundResource(R.drawable.ic_logout)

        button_Right.setOnClickListener {
            Log.e("logout pressed", "true")

            val dialogBuilder = AlertDialog.Builder(it.context)
            dialogBuilder.setMessage(R.string.logoutMessage)
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, which ->
                    activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                        ?.edit()
                        ?.clear()?.apply()
                    Intent(context, SignInUpActivity::class.java).also {
                        startActivity(it)
                        activity?.finish()
                    }
                })

            dialogBuilder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            dialogBuilder.create().show()


        }
        btn_reus_back.visibility = View.GONE

        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val apiInterface = ApiInterface.create()
        apiInterface.getCoursesParticipated(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                val courses = response.body()
                if (courses != null) {
                    Log.e("coursessssssssssssssssssssss     ", courses.toString())
                    val adapter = HomeCouseAdapter(courses, true)
                    Profile_Enrolled_courses.adapter = adapter
                    Profile_Enrolled_courses.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    Log.e("Username or Password wrong", "true")
                }
            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })


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

        ProfileSettings.setOnClickListener {
            Intent(activity, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }

        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(
                LASTNAME,
                ""
            ).toString()
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())


//        Profile_Participated_events.adapter = adapter
//        Profile_Enrolled_courses.adapter = adapter
//        Profile_Enrolled_courses.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        Profile_Participated_events.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onResume() {
        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(LASTNAME,"")
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())


        super.onResume()
    }

}