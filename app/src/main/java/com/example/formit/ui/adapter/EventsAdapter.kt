package com.example.formit.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Event
import com.example.formit.ui.view.activitys.DescriptionActivity
import com.example.formit.ui.view.activitys.DescriptionEventActivity
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.item_event.view.*


class EventsAdapter(var events: MutableList<Event>, var bookmarked: Boolean) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event_big, parent, false)
        return EventsViewHolder(view)
    }

    lateinit var mSharedPref: SharedPreferences
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        mSharedPref = holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        holder.itemView.apply {
            tv_EventName.text = events[position].eventName
            tv_LocationName.text = events[position].location.toString()
            tv_Hours.text = events[position].duration.toString() + " Hours"

            var test: Boolean
            test = false
            for (n in events[position].participatedMembers) {
                if (n.toString() == mSharedPref.getString(ID, "")) {
                    test = true
                }
            }
            Log.e("placesssssssssssssssssssssssssssssss", events[position].places.toString())
            setOnClickListener {
                val intent =
                    Intent(holder.itemView.context, DescriptionEventActivity::class.java).also {
                        it.putExtra("ID", events[position].id)
                        it.putExtra("NAME", events[position].eventName)
                        it.putExtra("Location", events[position].location.toString())
                        it.putExtra(
                            "DURATION",
                            events[position].duration.toString()
                        )
                        it.putExtra("DESCRIPTION", events[position].description)
                        it.putExtra("STARTDATE", events[position].startDate)
                        it.putExtra("PLACES", events[position].places.toString() + " Places")
                        it.putExtra("PARTICIPATED", test)
                    }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}