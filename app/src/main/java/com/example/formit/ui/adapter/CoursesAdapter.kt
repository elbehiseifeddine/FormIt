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
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.item_course.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesAdapter(var courses: MutableList<Course>, var bookmarked: Boolean) :

    RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {
    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_course_big, parent, false)
        return CoursesViewHolder(view)
    }
    var price: Int = 0
    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (mSharedPref.getInt(XP, 0) >= 500) {
            price = (courses[position].price * 0.9).toInt()
        } else {
            price = courses[position].price
        }
        if (bookmarked) {
            holder.itemView.apply {
                btn_bookmark.setImageResource(R.drawable.ic_bookmark)
                tv_CourseName.text = courses[position].courseName
                tv_Cost.text = price.toString() + " dt"
                tv_Hours.text = courses[position].duration.toString() + " Hours"
                tv_MentorName.text = courses[position].mentor.firstname

                btn_bookmark.setOnClickListener {

                    apiInterface.AddBookmark(mSharedPref.getString(ID, ""), courses[position].id)
                        .enqueue(object : Callback<User> {
                            override fun onResponse(
                                call: Call<User>, response:
                                Response<User>
                            ) {
                                val course = response.body()
                                if (course != null) {
                                    courses.removeAt(position)
                                    notifyDataSetChanged()
                                    Log.e("courses", course.toString())
                                } else {
                                    Log.e("Username or Password wrong", "true")
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                            }
                        })

                }
                var test: Boolean
                test = false
                for (n in courses[position].participatedMembers) {
                    if (n.toString() == mSharedPref.getString(ID, "")) {
                        test = true
                    }
                }
                Log.e("placesssssssssssssssssssssssssssssss", courses[position].places.toString())
                if(courses[position].participatedMembers.contains(mSharedPref.getString(ID, "")) ){
                    setOnClickListener {
                        val intent =
                            Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                                it.putExtra("ID", courses[position].id)
                                it.putExtra("NAME", courses[position].courseName)
                                it.putExtra("PRICE", price.toString() + " dt")
                                it.putExtra(
                                    "DURATION",
                                    courses[position].duration.toString() + " Hours"
                                )
                                it.putExtra("SUBSCRIBED", true)
                                it.putExtra("MENTOR", courses[position].mentor.firstname)
                                it.putExtra("DESCRIPTION", courses[position].description)
                                it.putExtra("PREREQUISITES", courses[position].prerequisites)
                                it.putExtra("STARTDATE", courses[position].startDate)
                                it.putExtra("PLACES", courses[position].places.toString() + " Places")
                                it.putExtra("PARTICIPATED", test)
                            }
                        holder.itemView.context.startActivity(intent)
                    }
                }else{
                    setOnClickListener {
                        val intent =
                            Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                                it.putExtra("ID", courses[position].id)
                                it.putExtra("NAME", courses[position].courseName)
                                it.putExtra("PRICE", price.toString() + " dt")
                                it.putExtra(
                                    "DURATION",
                                    courses[position].duration.toString() + " Hours"
                                )
                                it.putExtra("MENTOR", courses[position].mentor.firstname)
                                it.putExtra("DESCRIPTION", courses[position].description)
                                it.putExtra("PREREQUISITES", courses[position].prerequisites)
                                it.putExtra("STARTDATE", courses[position].startDate)
                                it.putExtra("PLACES", courses[position].places.toString() + " Places")
                                it.putExtra("PARTICIPATED", test)
                            }
                        holder.itemView.context.startActivity(intent)
                    }
                }


            }

        } else {
            holder.itemView.apply {
                tv_CourseName.text = courses[position].courseName
                tv_Cost.text = price.toString() + " dt"
                tv_Hours.text = courses[position].duration.toString() + " Hours"
                tv_MentorName.text = courses[position].mentor.firstname
                for (n in courses[position].usersbookmarked) {
                    if (n.toString() == mSharedPref.getString(ID, "")) {
                        btn_bookmark.setImageResource(R.drawable.ic_bookmark)
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
                                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                            }
                        })

                }
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DescriptionActivity::class.java).also {
                    it.putExtra("ID", courses[position].id)
                    it.putExtra("NAME", courses[position].courseName)
                    it.putExtra("PRICE", price.toString() + " dt")
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
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}