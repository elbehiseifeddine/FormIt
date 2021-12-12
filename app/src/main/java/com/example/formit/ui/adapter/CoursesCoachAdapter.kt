package com.example.formit.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.User
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.view.activitys.DescriptionActivity
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.item_coache_course_big.view.*
import kotlinx.android.synthetic.main.item_course.view.*
import kotlinx.android.synthetic.main.item_course.view.tv_CourseName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesCoachAdapter(var courses: MutableList<Course>) :
    RecyclerView.Adapter<CoursesCoachAdapter.CoursesViewHolder>() {
    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coache_course_big, parent, false)
        return CoursesViewHolder(view)
    }

    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

            holder.itemView.apply {
                tv_CourseName_Coache.text = courses[position].courseName
                tv_Cost_Coache.text = courses[position].price.toString() + " dt"
                tv_Hours_Coache.text = courses[position].duration.toString() + " Hours"
                tv_MentorName_Coache.text = courses[position].mentor.firstname

            }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                    it.putExtra("ID", courses[position].id)
                    it.putExtra("NAME", courses[position].courseName)
                    it.putExtra("PRICE", courses[position].price.toString() + " dt")
                    it.putExtra("DURATION", courses[position].duration.toString() + " Hours")
                    it.putExtra("MENTOR", courses[position].mentor.firstname)
                    it.putExtra("DESCRIPTION", courses[position].description)
                    it.putExtra("PREREQUISITES", courses[position].prerequisites)
                    it.putExtra("STARTDATE", courses[position].startDate)
                    it.putExtra("PLACES", courses[position].places.toString() + " Places")
                }
                holder.itemView.context.startActivity(intent)
            }

    }

    override fun getItemCount(): Int {
        return courses.size
    }
}