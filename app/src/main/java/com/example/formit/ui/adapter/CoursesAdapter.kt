package com.example.formit.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.view.DescriptionActivity
import kotlinx.android.synthetic.main.item_course.view.*

class CoursesAdapter(var courses: List<Course>) :
    RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {
    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.itemView.apply {
            tv_CourseName.text=courses[position].courseName
            tv_Cost.text=courses[position].price.toString() +" dt"
            tv_Hours.text=courses[position].duration.toString()+" Hours"
            tv_MentorName.text=courses[position].mentor
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}