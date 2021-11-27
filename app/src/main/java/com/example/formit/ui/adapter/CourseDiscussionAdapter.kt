package com.example.formit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Conversation
import com.example.formit.data.model.Course_Discussion
import kotlinx.android.synthetic.main.item_course_discussion.view.*

class CourseDiscussionAdapter(var CourseDiscussion : MutableList<Conversation>) :  RecyclerView.Adapter<CourseDiscussionAdapter.CourseDiscussionViewHolder>() {
    inner class CourseDiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDiscussionViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_course_discussion, parent, false)
        return CourseDiscussionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseDiscussionViewHolder, position: Int) {
        holder.itemView.apply {
           /* CourseDiscussionPic.setImageResource(CourseDiscussion[position].course.)
            CourseDiscussionName.text=CourseDiscussion[position].CourseDiscussionName
            CourseDiscussionLastMessage.text=CourseDiscussion[position].CourseDiscussionLastMessage
            CourseDiscussionTime.text=CourseDiscussion[position].CourseDiscussionTime
            CourseDiscussionImage1.setImageResource(CourseDiscussion[position].CourseDiscussionFirstPic)
            CourseDiscussionImage2.setImageResource(CourseDiscussion[position].CourseDiscussionSecondPic)
            CourseDiscussionImage3.setImageResource(CourseDiscussion[position].CourseDiscussionThirdPic)
            CourseDiscussionUnreaded.text=CourseDiscussion[position].CourseDiscussionUnreaded.toString()*/
            if (position==CourseDiscussion.size-1){
                SeparatorCourseDiscussion.visibility=View.GONE
            }
            if (position==0) {
                item_Course_Discussion.marginTop.countLeadingZeroBits()
            }

        }
    }

    override fun getItemCount(): Int = CourseDiscussion.size

}
