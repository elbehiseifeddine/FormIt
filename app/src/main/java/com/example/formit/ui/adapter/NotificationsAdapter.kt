package com.example.formit.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Notification
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.apiInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.item_course_discussion.view.*
import kotlinx.android.synthetic.main.item_notification.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsAdapter(var notifications: MutableList<Notification>, var idUser : String) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    private var removedItem: String = ""
    private var removedPosition: Int = 0
    private var refreshPosition: Int = 0
    private var text : String =""
    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val animation: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_from_right)
        holder.itemView.startAnimation(animation)
        holder.itemView.apply {

            val d = notifications[position].createdAt.time
            val now = System.currentTimeMillis()
            tv_Notification_Time.text= DateUtils.getRelativeTimeSpanString(d,now, DateUtils.SECOND_IN_MILLIS)
            tv_Notification_Object.text = notifications[position].notificationTitle
            tv_Notification_Description.text = notifications[position].notificationDescription





        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun removeItem(position: Int, viewHolder: NotificationViewHolder) {
        removedItem = notifications[position].notificationTitle
        removedPosition = position
        apiInterface.deleteNotification(idUser,notifications[position].id).enqueue(object :
            Callback<String> {
            override fun onResponse(
                call: Call<String>, response:
                Response<String>
            ) {
                val res = response.body()

                if(res.equals("done")){
                    notifications.removeAt(position)

                    notifyItemRemoved(position)
                    Snackbar.make(viewHolder.itemView, "$removedItem $text", Snackbar.LENGTH_LONG).show()
                    /*removedItem = users[position].username.toString()
                    removedPosition = position
                    var removedData = users[position]
                    users.removeAt(position)
                    notifyItemRemoved(position)
                    Snackbar.make(viewHolder.itemView, "$removedItem removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
                        users.add(removedPosition, removedData)
                        notifyItemInserted(removedPosition)
                    }.show()*/
                }



            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })

    }

    private fun deleteNotification(idNotification :String){

    }
}