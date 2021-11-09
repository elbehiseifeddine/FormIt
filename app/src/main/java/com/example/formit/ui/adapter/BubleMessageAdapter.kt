package com.example.formit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Buble_Message
import com.example.formit.data.model.Student
import kotlinx.android.synthetic.main.bulle_messages.view.*
import kotlinx.android.synthetic.main.item_buble_message.view.*

class BubleMessageAdapter(var Bubles: MutableList<Buble_Message>) : RecyclerView.Adapter<BubleMessageAdapter.BubleMessageViewHolder>(){

    inner class BubleMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubleMessageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_buble_message, parent, false)
        return BubleMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: BubleMessageViewHolder, position: Int) {
        holder.itemView.apply {
            imageBubleDiscussion.setImageResource(Bubles[position].profilePic)
            usernemaBubleDiscussion.setText(Bubles[position].userName)
        }
    }

    override fun getItemCount(): Int =Bubles.size
}