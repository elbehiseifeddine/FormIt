package com.example.formit.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Conversation
import com.example.formit.data.model.Course
import com.example.formit.ui.view.activitys.apiInterface
import com.example.formit.ui.view.chat.ChatActivity
import kotlinx.android.synthetic.main.item_coache_discussion.view.*
import kotlinx.android.synthetic.main.item_course_discussion.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CourseDiscussionAdapter(var CourseDiscussion : MutableList<Conversation>, var idUser:String, var name:String) :
    RecyclerView.Adapter<CourseDiscussionAdapter.CourseDiscussionViewHolder>() {
    inner class CourseDiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDiscussionViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_course_discussion, parent, false)
        return CourseDiscussionViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: CourseDiscussionViewHolder, position: Int) {
        //val course = getCourseById(CourseDiscussion[position].course)

        apiInterface.getCoursesById(CourseDiscussion[position].course).enqueue(object : Callback<Course> {
            override fun onResponse(
                call: Call<Course>, response:
                Response<Course>
            ) {
                val course = response.body()
                holder.itemView.apply {

                    val image = course!!.image


                    val uri = "@drawable/$image" // where myresource (without the extension) is the file


                    val imageResource = resources.getIdentifier(uri, null, context.packageName)

                    val res = resources.getDrawable(imageResource,null)
                    CourseDiscussionPic.setImageDrawable(res)
                    //CourseDiscussionPic.setImageResource(R.drawable)
                    CourseDiscussionName.text= course.courseName
                    if (CourseDiscussion[position].message.isNotEmpty()){
                        CourseDiscussionLastMessage.text=CourseDiscussion[position].message[CourseDiscussion[position].message.size-1].message

                        val d = CourseDiscussion[position].message[CourseDiscussion[position].message.size-1].createdAt.time
                        val now = System.currentTimeMillis()
                        CourseDiscussionTime.text=DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS)

                    }else {
                        CourseDiscussionLastMessage.text=""
                        CourseDiscussionTime.text=""

                    }

                    Log.e("---------sise--------",CourseDiscussion[position].members.size.toString())
                    if( CourseDiscussion[position].members.size==1) {
                        val path1 =
                            "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F" + CourseDiscussion[position].members[0].picture + "?alt=media"
                        Glide.with(context)
                            .load(path1)
                            .into(CourseDiscussionImage1)
                    }
                    if( CourseDiscussion[position].members.size==2) {
                        val path1 =
                            "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F" + CourseDiscussion[position].members[0].picture + "?alt=media"
                        Glide.with(context)
                            .load(path1)
                            .into(CourseDiscussionImage1)

                        val path2 = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+CourseDiscussion[position].members[1].picture+"?alt=media"
                        Glide.with(context)
                            .load(path2)
                            .into(CourseDiscussionImage2)
                    }

                    if( CourseDiscussion[position].members.size>=3) {
                        val path1 =
                            "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F" + CourseDiscussion[position].members[0].picture + "?alt=media"
                        Glide.with(context)
                            .load(path1)
                            .into(CourseDiscussionImage1)
                        Log.e("user image",CourseDiscussion[position].members[1].id+" "+CourseDiscussion[position].members[1].picture)
                        val path2 = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+CourseDiscussion[position].members[1].picture+"?alt=media"
                        Glide.with(context)
                            .load(path2)
                            .into(CourseDiscussionImage2)
                        val path3 = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+CourseDiscussion[position].members[2].picture+"?alt=media"
                        Glide.with(context)
                            .load(path3)
                            .into(CourseDiscussionImage3)
                    }

                   // CourseDiscussionImage1.setImageResource(R.drawable.test1)
                    //CourseDiscussionImage2.setImageResource(R.drawable.test2)
                    //CourseDiscussionImage3.setImageResource(R.drawable.test3)
                    if (position==CourseDiscussion.size-1){
                        SeparatorCourseDiscussion.visibility=View.GONE
                    }
                    if (position==0) {
                        item_Course_Discussion.marginTop.countLeadingZeroBits()
                    }

                }
                holder.itemView.setOnClickListener{
                    val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                        .apply{
                            putExtra("name", name)
                            putExtra("CourseName", course!!.courseName)
                            putExtra("idUser", idUser)
                            putExtra("idConversation", CourseDiscussion[position].id)
                        }
                    holder.itemView.context.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<Course>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })

    }



    override fun getItemCount(): Int = CourseDiscussion.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
