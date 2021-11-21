package com.example.formit.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.view.activitys.DescriptionActivity
import kotlinx.android.synthetic.main.item_course.view.*

class HomeCouseAdapter (var courses: MutableList<Course>) :
    RecyclerView.Adapter<HomeCouseAdapter.HomeCoursesViewHolder>() {
    inner class HomeCoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCoursesViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return HomeCoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCoursesViewHolder, position: Int) {
        holder.itemView.apply {
            tv_CourseName.text=courses[position].courseName
            tv_Cost.text=courses[position].price.toString() +" dt"
            tv_Hours.text=courses[position].duration.toString()+" Hours"
            tv_MentorName.text=courses[position].mentor
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                it.putExtra("NAME", courses[position].courseName)
                it.putExtra("PRICE", courses[position].price.toString() +" dt")
                it.putExtra("DURATION", courses[position].duration.toString()+" Hours")
                it.putExtra("MENTOR", courses[position].mentor)
                it.putExtra("DESCRIPTION", courses[position].description)
                it.putExtra("PREREQUISITES", courses[position].prerequisites)
                it.putExtra("STARTDATE", courses[position].startDate)
                it.putExtra("PLACES", courses[position].places)
            }
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        if(courses.size>2){
            return 3
        }
        return courses.size
    }
}