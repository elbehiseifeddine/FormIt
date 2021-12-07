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
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.item_course.view.*
import kotlinx.android.synthetic.main.item_course.view.tv_CourseName
import kotlinx.android.synthetic.main.item_course.view.tv_Hours
import kotlinx.android.synthetic.main.item_course.view.tv_MentorName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCouseAdapter(var courses: MutableList<Course>, var participated: Boolean) :
    RecyclerView.Adapter<HomeCouseAdapter.HomeCoursesViewHolder>() {
    inner class HomeCoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return HomeCoursesViewHolder(view)
    }

    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: HomeCoursesViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if(participated){
            holder.itemView.apply {
                tv_CourseName.text = courses[position].courseName
                tv_Cost.text = courses[position].price.toString() + " dt"
                tv_Hours.text = courses[position].duration.toString() + " Hours"
                tv_MentorName.text = courses[position].mentor


                btn_bookmark.visibility=View.GONE
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                    it.putExtra("ID", courses[position].id)
                    it.putExtra("NAME", courses[position].courseName)
                    it.putExtra("PRICE", courses[position].price.toString() + " dt")
                    it.putExtra("DURATION", courses[position].duration.toString() + " Hours")
                    it.putExtra("MENTOR", courses[position].mentor)
                    it.putExtra("DESCRIPTION", courses[position].description)
                    it.putExtra("PREREQUISITES", courses[position].prerequisites)
                    it.putExtra("STARTDATE", courses[position].startDate)
                    it.putExtra("SUBSCRIBED", true)
                    it.putExtra("PLACES", courses[position].places.toString() + " Places")
                }
                holder.itemView.context.startActivity(intent)
            }
        }

        if (!participated) {
            holder.itemView.apply {
                tv_CourseName.text = courses[position].courseName
                tv_Cost.text = courses[position].price.toString() + " dt"
                tv_Hours.text = courses[position].duration.toString() + " Hours"
                tv_MentorName.text = courses[position].mentor


                btn_bookmark.setTag(R.drawable.ic_bookmark_empty)
                for (n in courses[position].usersbookmarked) {
                    if (n.toString() == mSharedPref.getString(ID, "")) {
                        btn_bookmark.setImageResource(R.drawable.ic_bookmark)
                        btn_bookmark.setTag(R.drawable.ic_bookmark)
                    }
                }
                btn_bookmark.setOnClickListener {
                    if (btn_bookmark.getTag() == R.drawable.ic_bookmark) {
                        btn_bookmark.setImageResource(R.drawable.ic_bookmark_empty)
                        btn_bookmark.setTag(R.drawable.ic_bookmark_empty)
                    } else {
                        btn_bookmark.setImageResource(R.drawable.ic_bookmark)
                        btn_bookmark.setTag(R.drawable.ic_bookmark)
                    }
                    val apiInterface = ApiInterface.create()

                    apiInterface.AddBookmark(mSharedPref.getString(ID, ""), courses[position].id)
                        .enqueue(object : Callback<User> {
                            override fun onResponse(
                                call: Call<User>, response:
                                Response<User>
                            ) {
                                val courses = response.body()
                                if (courses != null) {
                                    Log.e("courses", courses.toString())
                                } else {
                                    Log.e("Username or Password wrong", "true")
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.e("bbbbbbbbbbbbbbbbbbbbbbb", "true")
                            }
                        })

                }
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                    it.putExtra("ID", courses[position].id)
                    it.putExtra("NAME", courses[position].courseName)
                    it.putExtra("PRICE", courses[position].price.toString() + " dt")
                    it.putExtra("DURATION", courses[position].duration.toString() + " Hours")
                    it.putExtra("MENTOR", courses[position].mentor)
                    it.putExtra("DESCRIPTION", courses[position].description)
                    it.putExtra("PREREQUISITES", courses[position].prerequisites)
                    it.putExtra("STARTDATE", courses[position].startDate)
                    it.putExtra("PLACES", courses[position].places.toString() + " Places")
                }
                holder.itemView.context.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int {
//        if(courses.size>2){
//            return 6
//        }
        return courses.size
    }
}