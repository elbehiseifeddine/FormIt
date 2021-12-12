package com.example.formit.ui.adapter

import android.content.Intent
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.ui.view.chat.ChatActivity
import com.example.formit.data.model.Coache_Discussion
import com.example.formit.data.model.Conversation
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_coache_discussion.view.*
import kotlinx.android.synthetic.main.item_course_discussion.view.*


class CoacheDiscussionAdapter(var CoachesDiscussion : MutableList<Conversation>, var idUser:String, var name:String) :
    RecyclerView.Adapter<CoacheDiscussionAdapter.CoacheDiscussionViewHolder>() {
    lateinit var mSharedPref: SharedPreferences
    inner class CoacheDiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoacheDiscussionViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_coache_discussion, parent, false)
        return CoacheDiscussionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoacheDiscussionViewHolder, position: Int) {

        if(CoachesDiscussion[position].members[0].id.equals(idUser)){
            val ConvoName=CoachesDiscussion[position].members[1].firstname+" "+CoachesDiscussion[position].members[1].lastname

            holder.itemView.apply {
                val path = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+CoachesDiscussion[position].members[1].picture+"?alt=media"
                Glide.with(context)
                    .load(path)
                    .into(CoacheDiscussionPic)

                CoacheDiscussionName.text = ConvoName
                if (CoachesDiscussion[position].message.isNotEmpty()) {
                    CoacheDiscussionLastMessage.text =
                        CoachesDiscussion[position].message[CoachesDiscussion[position].message.size - 1].message


                val d = CoachesDiscussion[position].message[CoachesDiscussion[position].message.size-1].createdAt.time
                val now = System.currentTimeMillis()
                CoacheDiscussionTime.text = DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS)

            }else {
                    CoacheDiscussionLastMessage.text=""
                    CoacheDiscussionTime.text=""

            }
                if (position == CoachesDiscussion.size - 1) {
                    SeparatorCoacheDiscussion.visibility = View.GONE
                }
                if (position == 0) {
                    item_Coache_Discussion.marginTop.countLeadingZeroBits()
                }
            }
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                    .apply{
                        putExtra("name", name)
                        putExtra("CourseName", ConvoName)
                        putExtra("idUser", idUser)
                        putExtra("idConversation", CoachesDiscussion[position].id)
                    }
                holder.itemView.context.startActivity(intent)
            }
        }else{
            val ConvoName=CoachesDiscussion[position].members[0].firstname+" "+CoachesDiscussion[position].members[1].lastname

            holder.itemView.apply {
                val path = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+CoachesDiscussion[position].members[0].picture+"?alt=media"
                Glide.with(context)
                    .load(path)
                    .into(CoacheDiscussionPic)

                CoacheDiscussionName.text = ConvoName
                if (CoachesDiscussion[position].message.isNotEmpty()) {
                    CoacheDiscussionLastMessage.text =
                        CoachesDiscussion[position].message[CoachesDiscussion[position].message.size - 1].message


                    val d = CoachesDiscussion[position].message[CoachesDiscussion[position].message.size-1].createdAt.time
                    val now = System.currentTimeMillis()
                    CoacheDiscussionTime.text = DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS)

                }else {
                    CoacheDiscussionLastMessage.text=""
                    CoacheDiscussionTime.text=""

                }
                if (position == CoachesDiscussion.size - 1) {
                    SeparatorCoacheDiscussion.visibility = View.GONE
                }
                if (position == 0) {
                    item_Coache_Discussion.marginTop.countLeadingZeroBits()
                }
            }
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                    .apply{
                        putExtra("name", name)
                        putExtra("CourseName", ConvoName)
                        putExtra("idUser", idUser)
                        putExtra("idConversation", CoachesDiscussion[position].id)
                    }
                holder.itemView.context.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int = CoachesDiscussion.size
}