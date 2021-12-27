package com.example.formit.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Notification
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.item_notification.view.*


class NotificationsAdapter(var notifications: MutableList<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.EventsViewHolder>() {
    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return EventsViewHolder(view)
    }

    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val animation: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_from_right)
        holder.itemView.startAnimation(animation)
        holder.itemView.apply {
            tv_Notification_Object.text = notifications[position].notificationTitle
            tv_Notification_Description.text = notifications[position].notificationDescription
        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}