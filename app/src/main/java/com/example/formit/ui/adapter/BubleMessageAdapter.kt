package com.example.formit.ui.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Buble_Message
import kotlinx.android.synthetic.main.item_bulle_messages_connected.view.*
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
            if (Bubles[position].userConnected){
                userConnected.visibility = View.VISIBLE
            }else{
                userConnected.visibility= View.GONE
            }
        }
    }

    override fun getItemCount(): Int =Bubles.size
}