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
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.Event
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.adapter.HomeEventAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.fragment_home.*
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
        btn_ProfileAchievements.setOnClickListener {
            Intent(context, AchievementActivity::class.java).also {
                startActivity(it)
            }
        }
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
        if (mSharedPref.getString(ROLE, "").toString() == "coache") {
            Profile_Enrolled_courses.visibility=View.GONE
            Profile_Participated_events.visibility=View.GONE
            CourseTextLayout.visibility=View.GONE
            EventTextLayout.visibility=View.GONE
        }else {

            /*activity?.runOnUiThread{
                LoadUserParticipatedData()
            }*/
            LoadUserParticipatedData()

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
        }
        ProfileSettings.setOnClickListener {
            Intent(activity, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }
        if(mSharedPref.getString(PICTURE, "").toString()=="avatar default.png")
        {
            ProfilePicture!!.setImageResource(R.drawable.male_student)
        }
        else
        {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+filename2+"?alt=media"
            Log.e("*******************************path image ",path)
            Glide.with(requireActivity())
                .load(path)
                .into(ProfilePicture)
        }
        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(LASTNAME,"").toString()
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())


//        Profile_Participated_events.adapter = adapter
//        Profile_Enrolled_courses.adapter = adapter
//        Profile_Enrolled_courses.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        Profile_Participated_events.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if(mSharedPref.getInt(XP, 0)<500){
            iv_AchievmentId.setImageResource(R.drawable.ic_bronze)
            tv_Achievement.text="Bronze"
        }else if (mSharedPref.getInt(XP, 0)>=500 && mSharedPref.getInt(XP, 0)<1000){
            iv_AchievmentId.setImageResource(R.drawable.ic_sliver)
            tv_Achievement.text="Silver"
        }else if (mSharedPref.getInt(XP, 0)>=1000){
            iv_AchievmentId.setImageResource(R.drawable.ic_gold)
            tv_Achievement.text="Gold"
        }
    }

    override fun onResume() {
        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(LASTNAME,"")
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())


        super.onResume()
    }

    fun LoadUserParticipatedData(){
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

        apiInterface.getEventsParticipated(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Event>> {
            override fun onResponse(
                call: Call<MutableList<Event>>, response:
                Response<MutableList<Event>>
            ) {
                val events = response.body()
                if (events != null) {
                    Log.e("coursessssssssssssssssssssss     ", events.toString())
                    val adapter = HomeEventAdapter(events, true)
                    Profile_Participated_events.adapter = adapter
                    Profile_Participated_events.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    Log.e("Username or Password wrong", "true")
                }
            }

            override fun onFailure(call: Call<MutableList<Event>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })

    }

}