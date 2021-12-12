package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.Event
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import com.example.formit.ui.adapter.EventsAdapter
import com.example.formit.ui.adapter.HomeEventAdapter
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        apiInterface.getEventsNotParticipated(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Event>> {
            override fun onResponse(
                call: Call<MutableList<Event>>, response:
                Response<MutableList<Event>>
            ) {
                val events = response.body()
                if (events != null) {
                    Log.e("coursesaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",events.toString())
                    val adapter= EventsAdapter(events,false)
                    rv_Cour.adapter=adapter
                    rv_Cour.layoutManager =
                        LinearLayoutManager(this@EventsActivity, LinearLayoutManager.VERTICAL, false)

                } else {
                    Log.e("Username or Password wrong","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Event>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
            }
        })

        toolbar_title.text="Events"
        button_Right.visibility= View.INVISIBLE

        btn_reus_back.setOnClickListener {
            finish()
        }

    }
}