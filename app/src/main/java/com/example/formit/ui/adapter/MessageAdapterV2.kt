package com.example.formit.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.data.model.Message
import org.json.JSONObject

class MessageAdapterV2(var messages: MutableList<Message>) :
    RecyclerView.Adapter<MessageAdapterV2.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int =messages.size

    fun addItem(jsonObject: JSONObject?) {

        notifyDataSetChanged()
    }
}