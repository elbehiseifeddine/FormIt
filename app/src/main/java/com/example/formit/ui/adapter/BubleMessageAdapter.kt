package com.example.formit.ui.adapter

import android.content.Intent
import android.opengl.Visibility
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Buble_Message
import com.example.formit.data.model.Conversation
import com.example.formit.ui.view.chat.ChatActivity
import kotlinx.android.synthetic.main.item_bulle_messages_connected.view.*
import kotlinx.android.synthetic.main.item_buble_message.view.*
import kotlinx.android.synthetic.main.item_coache_discussion.view.*

class BubleMessageAdapter(var Bubles: MutableList<Conversation>, var idUser:String, var name:String) : RecyclerView.Adapter<BubleMessageAdapter.BubleMessageViewHolder>(){

    inner class BubleMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubleMessageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_buble_message, parent, false)
        return BubleMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: BubleMessageViewHolder, position: Int) {

        if (Bubles[position].members[0].id == idUser) {
            val ConvoName =
                Bubles[position].members[1].firstname + " " + Bubles[position].members[1].lastname

            holder.itemView.apply {
                val path =
                    "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F" + Bubles[position].members[1].picture + "?alt=media"
                Glide.with(context)
                    .load(path)
                    .into(imageBubleDiscussion)


            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                    .apply {
                        putExtra("name", name)
                        putExtra("CourseName", ConvoName)
                        putExtra("idUser", idUser)
                        putExtra("idConversation", Bubles[position].id)
                    }
                holder.itemView.context.startActivity(intent)
            }
        } else {
            val ConvoName =
                Bubles[position].members[0].firstname + " " + Bubles[position].members[1].lastname

            holder.itemView.apply {
                val path =
                    "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F" + Bubles[position].members[0].picture + "?alt=media"
                Glide.with(context)
                    .load(path)
                    .into(imageBubleDiscussion)


            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                    .apply {
                        putExtra("name", name)
                        putExtra("CourseName", ConvoName)
                        putExtra("idUser", idUser)
                        putExtra("idConversation", Bubles[position].id)
                    }
                holder.itemView.context.startActivity(intent)
            }
        }
        holder.itemView.apply {


                userConnected.visibility= View.GONE

        }
    }

    override fun getItemCount(): Int =Bubles.size
}