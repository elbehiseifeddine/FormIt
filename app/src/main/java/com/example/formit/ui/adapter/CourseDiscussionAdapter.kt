package com.example.formit.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Conversation
import com.example.formit.data.model.Message
import com.example.formit.ui.view.chat.ChatActivity
import kotlinx.android.synthetic.main.item_course_discussion.view.*


class CourseDiscussionAdapter(var CourseDiscussion : MutableList<Conversation>, var idUser:String, var name:String) :
    RecyclerView.Adapter<CourseDiscussionAdapter.CourseDiscussionViewHolder>() {
    inner class CourseDiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDiscussionViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_course_discussion, parent, false)
        return CourseDiscussionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseDiscussionViewHolder, position: Int) {
        holder.itemView.apply {

            var image = CourseDiscussion[position].course.image


            val uri = "@drawable/$image" // where myresource (without the extension) is the file


            val imageResource = resources.getIdentifier(uri, null, context.packageName)

            val res = resources.getDrawable(imageResource,null)
            CourseDiscussionPic.setImageDrawable(res)
            //CourseDiscussionPic.setImageResource(R.drawable)
            CourseDiscussionName.text=CourseDiscussion[position].course.courseName
            if (!CourseDiscussion[position].message.isEmpty()){
                CourseDiscussionLastMessage.text=CourseDiscussion[position].message[CourseDiscussion[position].message.size-1].message
                CourseDiscussionTime.text=CourseDiscussion[position].message[CourseDiscussion[position].message.size-1].createdAt.toString()

            }else {
                CourseDiscussionLastMessage.text=""
                CourseDiscussionTime.text=""

            }
            CourseDiscussionImage1.setImageResource(R.drawable.test1)
            CourseDiscussionImage2.setImageResource(R.drawable.test2)
            CourseDiscussionImage3.setImageResource(R.drawable.test3)
            CourseDiscussionUnreaded.text="9"
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
                    putExtra("CourseName", CourseDiscussion[position].course.courseName)
                    putExtra("idUser", idUser)
                    putExtra("idConversation", CourseDiscussion[position].id)
                }
            holder.itemView.context.startActivity(intent)
        }
    }

    /*fun update(msg : Message){
        var holder: CourseDiscussionViewHolder?= null
        holder!!.itemView.CourseDiscussionLastMessage.text=msg.message
        holder!!.itemView.CourseDiscussionTime.text=msg.createdAt.toString()
        notifyDataSetChanged()

    }*/

    override fun getItemCount(): Int = CourseDiscussion.size

}
